package com.example.platformapi.persistence.repository

import com.example.platformapi.persistence.model.Balance
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.util.*

interface BalanceRepository : JpaRepository<Balance, String> {
    /**
     * Этот метод гарантирует, что полученная запись баланса будет заблокирована для других транзакций на уровне записи,
     * предотвращая одновременное изменение данных другими транзакциями.
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findWithPessimisticLockByUserId(userId: String): Optional<Balance>
}
