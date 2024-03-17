package com.example.platformapi.mapper

import com.example.platformapi.dto.BalanceDTO
import com.example.platformapi.persistence.model.Balance

object BalanceMapper {
    fun toDTO(balance: Balance) = BalanceDTO(
        userId = balance.userId,
        userNick = balance.userNick,
        amount = balance.amount,
        currency = balance.currency,
        denomination = balance.denomination,
        maxWin = balance.maxWin,
    )
}
