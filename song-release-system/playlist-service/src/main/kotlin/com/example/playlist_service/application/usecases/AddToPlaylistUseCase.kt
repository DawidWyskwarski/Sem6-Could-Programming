package com.example.playlist_service.application.usecases

import com.example.playlist_service.application.ports.PlaylistRepository
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * Use case for adding new track to global playlists based on common tags.
 */
class AddToPlaylistUseCase (
    private val repository: PlaylistRepository
) {
    private val logger = LoggerFactory.getLogger(AddToPlaylistUseCase::class.java)

    fun execute(trackId: UUID, trackTagIds: Set<UUID>) {
        logger.info("Executing AddToPlaylistUseCase for trackId={} with {} tags", trackId, trackTagIds.size)

        val playlists = repository.findPlaylistsWithMinimumCommonTags(trackTagIds, 1)
        logger.info("Found {} eligible playlists for trackId={}", playlists.size, trackId)

        playlists.forEach { pl ->
            logger.debug("Adding trackId={} to playlist id={}, name='{}'", trackId, pl.id, pl.name)
            repository.addToPlaylist(pl, trackId)
        }

        logger.info("Finished adding trackId={} to {} playlists", trackId, playlists.size)
    }
}