package com.example.file_service.infrastructure.exception

import com.example.file_service.domain.exception.FileDomainException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.slf4j.LoggerFactory

/**
 * Global exception handler for file-related domain exceptions.
 * Catches FileDomainException and returns a structured error response with a 400 Bad Request status
 */
@RestControllerAdvice
class FileExceptionHandler {

    private val logger = LoggerFactory.getLogger(FileExceptionHandler::class.java)

    @ExceptionHandler(FileDomainException::class)
    fun handleFileDomainException(ex: FileDomainException): ResponseEntity<Map<String, String>> {
        logger.warn("Domain exception occurred: {}", ex.message)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to (ex.message ?: "Invalid file payload")))
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<Map<String, String>> {
        logger.warn("Illegal state exception occurred: {}", ex.message)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to (ex.message ?: "Illegal state")))
    }
}
