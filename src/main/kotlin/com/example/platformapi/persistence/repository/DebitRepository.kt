package com.example.platformapi.persistence.repository

import com.example.platformapi.persistence.model.Debit
import org.springframework.data.jpa.repository.JpaRepository

interface DebitRepository : JpaRepository<Debit, String> {
    fun findAllByUserId(gameSessionId: String): List<Debit>
    fun findByBetId(betId: String): Debit?
}
