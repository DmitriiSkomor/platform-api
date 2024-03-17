package com.example.platformapi.dto

data class OperationResponseDTO(
    val api: String,
    val data: Any,
    val isSuccess: Boolean = true,
    val error: String? = null,
)

data class DebitResponseDTO(
    val betId: String,
    val userNick: String,
    val amount: Long,
    val denomination: Int,
    val maxWin: Long,
    val currency: String,
)

data class CreditResponseDTO(
    val betId: String,
    val userNick: String,
    val amount: Long,
    val denomination: Int,
    val maxWin: Long,
    val currency: String,
)

data class BalanceDTO(
    val userId: String,
    val userNick: String,
    var amount: Long,
    val currency: String,
    val denomination: Int,
    val maxWin: Long,
)
