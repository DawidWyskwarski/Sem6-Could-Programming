package com.example.file_service.domain.queries

import com.example.common.queries.Query
import java.time.Instant
import java.util.UUID

class GetTrackMusicFileQuery(
    val trackId: UUID,
    override val timestamp: Instant = Instant.now()
): Query<ByteArray>