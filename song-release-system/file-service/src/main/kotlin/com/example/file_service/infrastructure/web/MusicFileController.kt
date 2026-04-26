package com.example.file_service.infrastructure.web

import com.example.common.mediator.Mediator
import com.example.file_service.domain.commands.AttachFileToTrackCommand
import com.example.file_service.domain.queries.GetTrackMusicFileQuery
import org.slf4j.LoggerFactory
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File
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

    @PostMapping()
    fun attachFileToTrack(
        @RequestParam(value = "trackId") trackId: UUID,
        @RequestPart("file") file: MultipartFile
    ): ResponseEntity<Unit> {
        logger.info(
            "Received file to attach: name={}, contentType={}, size={}",
            file.originalFilename,
            file.contentType,
            file.size
        )

        val tempFile = file.toTempFile()
        try {
            mediator.send(
                AttachFileToTrackCommand(
                    trackId = trackId,
                    file = tempFile
                )
            )
        } finally {
            tempFile.delete()
        }

        logger.info("Successfully initiated file attach usecase for trackId={}", trackId)

        return ResponseEntity.status(HttpStatus.CREATED).build()
    }

    @GetMapping("/{trackId}/file")
    fun getFile(@PathVariable trackId: UUID): ResponseEntity<ByteArrayResource> {
        logger.info("Received request to get file for trackId={}", trackId)

        val result = mediator.send(
            GetTrackMusicFileQuery(
                trackId = trackId
            )
        )

        return ResponseEntity.status(HttpStatus.OK).body(ByteArrayResource(result))
    }

    private fun MultipartFile.toTempFile(): File {
        val tempFile = File.createTempFile("upload-", "-${originalFilename}")
        transferTo(tempFile)
        return tempFile
    }
}