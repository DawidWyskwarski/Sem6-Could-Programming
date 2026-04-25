package com.example.file_service.infrastructure.web

import com.example.common.mediator.Mediator
import com.example.file_service.domain.commands.AttachFileToTrackCommand
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.util.*

/**
 * Simple API controller for attaching files to tracks.
 *
 * http://localhost/api/files/attach
 */
@RestController()
@RequestMapping("/api/files")
class MusicFileController(
    val mediator: Mediator,
) {

    private val logger = LoggerFactory.getLogger(MusicFileController::class.java)

    @PostMapping("/attach")
    fun attachFileToTrack(
        @RequestParam(value = "trackId") trackId: UUID,
        @RequestParam(value = "file") file: MultipartFile
    ) {
        logger.info(
            "Received file to attach: name={}, contentType={}, size={}",
            file.originalFilename,
            file.contentType,
            file.size
        )

        mediator.send(
            AttachFileToTrackCommand(
                trackId = trackId,
                fileName = file.originalFilename ?: "",
                file = file.bytes
            )
        )

        logger.info("Successfully initiated file attach usecase for trackId={}", trackId)
    }
}