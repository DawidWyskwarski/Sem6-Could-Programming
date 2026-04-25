package com.example.track_service.infrastructure.web

import com.example.common.mediator.Mediator
import com.example.track_service.domain.commands.CreateTrackCommand
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * Simple API controller used only for creating new tracks.
 *
 * http://localhost:8081/api/track/create
 */
@RestController()
@RequestMapping("/api/tracks")
class TrackController(
    val mediator: Mediator,
) {

    private val logger = LoggerFactory.getLogger(TrackController::class.java)

    @PostMapping("/create")
    fun createTrack(
        @RequestParam(value = "title") title: String,
        @RequestParam(value = "artistId") artistId: UUID,
        @RequestParam(value = "keywords") keywords: List<String>
    ) : ResponseEntity<Map<String, String>> {

        logger.info("Received request to create track with title={}, artistId={}", title, artistId)

        val trackId = mediator.send(
            CreateTrackCommand(title, artistId, keywords)
        )

        logger.info("Successfully created track with id={}", trackId)

        // Return the ID of newly created track.
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(mapOf("trackId" to trackId.toString()))
    }
}