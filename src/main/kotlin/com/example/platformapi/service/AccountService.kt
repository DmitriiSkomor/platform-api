package com.example.platformapi.service

import com.example.platformapi.dto.*
import com.example.platformapi.exception.ErrorMessages.ALREADY_PROCESSED
import com.example.platformapi.exception.ErrorMessages.BALANCE_NOT_FOUND
import com.example.platformapi.exception.ErrorMessages.DEBIT_TRANSACTION_NOT_FOUND
import com.example.platformapi.exception.ErrorMessages.INSUFFICIENT_BALANCE
import com.example.platformapi.exception.ErrorMessages.SESSION_NOT_FOUND
import com.example.platformapi.mapper.BalanceMapper
import com.example.platformapi.mapper.CreditMapper
import com.example.platformapi.mapper.DebitMapper
import com.example.platformapi.persistence.model.Balance
import com.example.platformapi.persistence.repository.BalanceRepository
import com.example.platformapi.persistence.repository.DebitRepository
import com.example.platformapi.persistence.repository.SessionRepository
import jakarta.transaction.Transactional
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AccountService(
    private val balanceRepository: BalanceRepository,
    private val sessionRepository: SessionRepository,
    private val debitRepository: DebitRepository,
) {

    fun getBalance(balanceRequest: BalanceRequestDTO): BalanceDTO {
        return sessionRepository.findById(balanceRequest.gameSessionId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, SESSION_NOT_FOUND) }
            .userId
            .let { userId ->
                balanceRepository.findById(userId)
                    .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, BALANCE_NOT_FOUND) }
            }.let(BalanceMapper::toDTO)
    }

    @Transactional
    fun debitAmount(debitRequest: DebitRequestDTO): DebitResponseDTO {
        debitRepository.findById(debitRequest.betId).ifPresent {
            throw ResponseStatusException(HttpStatus.CONFLICT, ALREADY_PROCESSED)
        }

        return findBalanceBySessionIdWithPessimisticLock(debitRequest.gameSessionId)
            .also { balance -> checkSufficientBalance(balance, debitRequest) }
            .let { balance ->
                debitRepository.save(DebitMapper.toDb(debitRequest, balance.userId))
                    .let { debit -> DebitMapper.toDTO(debit, balance) }
            }
    }

    @Transactional
    fun creditAmount(creditRequest: CreditRequestDTO): CreditResponseDTO {
        return debitRepository.findByBetId(creditRequest.betId)?.also { debit ->
            debitRepository.delete(debit)
        }?.let { debit ->
            findBalanceBySessionIdWithPessimisticLock(creditRequest.gameSessionId).apply {
                // Проверяем, является ли сумма кредитования положительной (выигрыш)
                // Если да, увеличиваем баланс на сумму выигрыша
                // Если нет, уменьшаем баланс на сумму ставки (проигрыш)
                amount += if (creditRequest.amount > 0) creditRequest.amount else -debit.amount
            }.also { balance ->
                balanceRepository.save(balance)
            }.let { balance ->
                CreditMapper.toDTO(debit, balance)
            }
        } ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, DEBIT_TRANSACTION_NOT_FOUND)
    }

    private fun findBalanceBySessionIdWithPessimisticLock(sessionId: String): Balance {
        return sessionRepository.findById(sessionId)
            .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, SESSION_NOT_FOUND) }
            .userId
            .let { userId ->
                balanceRepository.findWithPessimisticLockByUserId(userId)
                    .orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND, BALANCE_NOT_FOUND) }
            }
    }

    private fun checkSufficientBalance(balance: Balance, debitRequest: DebitRequestDTO) {
        val totalDebits = debitRepository.findAllByUserId(balance.userId).sumOf { it.amount }
        if (balance.amount < totalDebits + debitRequest.amount) {
            throw ResponseStatusException(HttpStatus.CONFLICT, INSUFFICIENT_BALANCE)
        }
    }
}
