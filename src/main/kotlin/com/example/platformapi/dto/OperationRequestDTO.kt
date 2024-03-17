package com.example.platformapi.dto

import com.example.platformapi.dto.deserializer.OperationRequestDTODeserializer
import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.databind.annotation.JsonDeserialize

interface OperationDTO

enum class OperationType(val value: String) {
    BALANCE("balance"),
    DEBIT("debit"),
    CREDIT("credit"),
}

@JsonDeserialize(using = OperationRequestDTODeserializer::class)
data class OperationRequestDTO(val api: String, val data: OperationDTO)

/**
 * @JsonCreator явно указывает Jackson на использование данного конструктора для десериализации,
 * что необходимо для корректной обработки классов с одним полем.
 */
data class BalanceRequestDTO @JsonCreator constructor(val gameSessionId: String) : OperationDTO

data class DebitRequestDTO(
    val betId: String,
    val gameSessionId: String,
    val amount: Long,
) : OperationDTO

data class CreditRequestDTO(
    val betId: String,
    val gameSessionId: String,
    val amount: Long,
) : OperationDTO
