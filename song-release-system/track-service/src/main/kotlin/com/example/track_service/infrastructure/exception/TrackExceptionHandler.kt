package com.example.track_service.infrastructure.exception

import com.example.track_service.domain.exception.TrackDomainException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.slf4j.LoggerFactory

/**
 * Main exception handler for the Track Service. This class captures domain-specific exceptions and translates them into appropriate HTTP responses.
 * It ensures that clients receive meaningful error messages while also logging the exceptions for debugging purposes.
 */
@RestControllerAdvice
class TrackExceptionHandler {

    private val logger = LoggerFactory.getLogger(TrackExceptionHandler::class.java)

    @ExceptionHandler(TrackDomainException::class)
    fun handleTrackDomainException(ex: TrackDomainException): ResponseEntity<Map<String, String>> {
        logger.warn("Domain exception occurred while processing track: {}", ex.message)
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(mapOf("error" to (ex.message ?: "Invalid track configuration")))
    }
}
