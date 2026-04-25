package com.example.playlist_service.application.ports

import com.example.playlist_service.domain.model.Playlist
import java.util.UUID

/**
 * Port interface for Playlist repository operations.
 * This interface defines the contract for retrieving playlists based on common tags and adding tracks to playlists.
 */
interface PlaylistRepository {
    fun findPlaylistsWithMinimumCommonTags(tagIds: Set<UUID>, minCommon: Int): List<Playlist>
    fun addToPlaylist(playlist: Playlist, trackId: UUID)
}