package com.example.platformapi.dto.deserializer

import com.example.platformapi.dto.*
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class OperationRequestDTODeserializer : JsonDeserializer<OperationRequestDTO>() {
    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): OperationRequestDTO {
        val node: JsonNode = jp.codec.readTree(jp)
        return OperationRequestDTO(
            api = node.get("api").asText(),
            data = node.get("data").let { dataNode ->
                when (val api = node.get("api").asText()) {
                    OperationType.BALANCE.value -> jp.codec.treeToValue(dataNode, BalanceRequestDTO::class.java)
                    OperationType.DEBIT.value -> jp.codec.treeToValue(dataNode, DebitRequestDTO::class.java)
                    OperationType.CREDIT.value -> jp.codec.treeToValue(dataNode, CreditRequestDTO::class.java)
                    else -> throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported operation type: $api")
                }
            },
        )
    }
}
