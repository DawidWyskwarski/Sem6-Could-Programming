package com.example.file_service.domain.commands

import com.example.common.commands.Command
import com.example.file_service.domain.exception.EmptyFileException
import com.example.file_service.domain.exception.InvalidFileExtensionException
import com.example.file_service.domain.exception.InvalidFileSizeException
import java.io.File
import java.time.Instant
import java.util.UUID
import kotlin.compareTo

class AttachFileToTrackCommand(
    val trackId: UUID,
    val file: File,
    override val timestamp: Instant = Instant.now()
): Command<Unit>