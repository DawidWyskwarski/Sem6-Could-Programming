package com.example.file_service.domain.commands

import com.example.common.commands.Command
import java.time.Instant
import java.util.UUID

class AttachFileToTrackCommand(
    val trackId: UUID,
    val fileName: String,
    val file: ByteArray,
    override val timestamp: Instant = Instant.now()
): Command<Unit>