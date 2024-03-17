package com.example.platformapi.exception

import com.example.platformapi.exception.ErrorMessages.AN_ERROR_OCCURRED
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.server.ResponseStatusException

@ControllerAdvice
class ErrorHandler {
    private val logger: Logger = LoggerFactory.getLogger(ErrorHandler::class.java)

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(ex: ResponseStatusException, request: WebRequest?): ResponseEntity<Any> {
        logger.error("ResponseStatusException: {}", ex.message, ex)

        val body: Map<String, Any> = mapOf(
            "message" to (ex.reason ?: AN_ERROR_OCCURRED),
        )

        return ResponseEntity(body, ex.statusCode)
    }
}
