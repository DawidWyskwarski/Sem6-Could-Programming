package com.example.playlist_service.domain.model

import java.util.UUID

/**
 * Represents a musical playlist containing a collection of tags.
 *
 * @property id The unique identifier for the playlist.
 * @property name The name of the playlist.
 * @property tagIds A list of unique identifiers for tags associated with this playlist.
 */
data class Playlist(
    val id: UUID,
    val name: String,
    val tagIds: List<UUID>
)
