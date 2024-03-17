package com.example.platformapi.mapper

import com.example.platformapi.dto.CreditResponseDTO
import com.example.platformapi.persistence.model.Balance
import com.example.platformapi.persistence.model.Debit

object CreditMapper {
    fun toDTO(debit: Debit, balance: Balance) = CreditResponseDTO(
        betId = debit.betId,
        userNick = balance.userNick,
        amount = balance.amount,
        denomination = balance.denomination,
        maxWin = balance.maxWin,
        currency = balance.currency,
    )
}
