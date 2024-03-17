package com.example.platformapi.api

import com.example.platformapi.dto.*
import com.example.platformapi.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/open-api-games/v1/games-processor")
class GamingOperationsController(
    private val accountService: AccountService,
) {

    @PostMapping
    fun processGameOperation(@RequestBody operationRequest: OperationRequestDTO): ResponseEntity<OperationResponseDTO> {
        return when (operationRequest.api) {
            OperationType.BALANCE.value ->
                ResponseEntity.ok(
                    OperationResponseDTO(
                        operationRequest.api,
                        accountService.getBalance(operationRequest.data as BalanceRequestDTO),
                    ),
                )

            OperationType.DEBIT.value ->
                ResponseEntity.ok(
                    OperationResponseDTO(
                        operationRequest.api,
                        accountService.debitAmount(operationRequest.data as DebitRequestDTO),
                    ),
                )

            OperationType.CREDIT.value ->
                ResponseEntity.ok(
                    OperationResponseDTO(
                        operationRequest.api,
                        accountService.creditAmount(operationRequest.data as CreditRequestDTO),
                    ),
                )

            else -> throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "Operation ${operationRequest.api} is not supported",
            )
        }
    }
}
