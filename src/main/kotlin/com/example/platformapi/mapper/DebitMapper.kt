package com.example.platformapi.mapper

import com.example.platformapi.dto.DebitRequestDTO
import com.example.platformapi.dto.DebitResponseDTO
import com.example.platformapi.persistence.model.Balance
import com.example.platformapi.persistence.model.Debit

object DebitMapper {
    fun toDb(debitRequest: DebitRequestDTO, userId: String) = Debit(
        betId = debitRequest.betId,
        userId = userId,
        amount = debitRequest.amount,
    )

    fun toDTO(debit: Debit, balance: Balance) = DebitResponseDTO(
        betId = debit.betId,
        userNick = balance.userNick,
        amount = balance.amount,
        denomination = balance.denomination,
        maxWin = balance.maxWin,
        currency = balance.currency,
    )
}
