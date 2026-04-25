package com.example.track_service.application.ports

import com.example.track_service.domain.model.Track
import java.util.UUID

/**
 * Port interface for Track repository operations.
 * This interface defines the contract for saving and retrieving Track entities from the data store.
 */
interface TrackRepository {
    fun save(track: Track)
    fun findById(id: UUID): Track?
}