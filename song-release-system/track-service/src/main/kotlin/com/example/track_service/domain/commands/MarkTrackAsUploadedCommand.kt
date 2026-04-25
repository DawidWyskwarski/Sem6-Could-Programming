package com.example.track_service.domain.commands

import com.example.common.commands.Command
import java.util.UUID

class MarkTrackAsUploadedCommand(
    val trackId: UUID,
    override val timestamp: java.time.Instant = java.time.Instant.now()
): Command<Unit>