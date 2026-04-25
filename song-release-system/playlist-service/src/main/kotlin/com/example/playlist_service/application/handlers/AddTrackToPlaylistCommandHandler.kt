package com.example.playlist_service.application.handlers

import com.example.common.commands.CommandHandler
import com.example.playlist_service.application.ports.PlaylistRepository
import com.example.playlist_service.domain.commands.AddTrackToPlaylistCommand
import org.slf4j.LoggerFactory

class AddTrackToPlaylistCommandHandler(
    private val repository: PlaylistRepository
): CommandHandler<AddTrackToPlaylistCommand, Unit> {

    private val logger = LoggerFactory.getLogger(AddTrackToPlaylistCommandHandler::class.java)

    override fun handle(command: AddTrackToPlaylistCommand) {

        val trackId = command.trackId
        val trackTagIds = command.trackTagIds

        logger.info("Executing AddToPlaylistUseCase for trackId=$trackId with ${trackTagIds.size} tags")

        val playlists = repository.findPlaylistsWithMinimumCommonTags(trackTagIds, 1)
        logger.info("Found ${playlists.size} eligible playlists for trackId=$trackId")

        playlists.forEach { pl ->
            logger.debug("Adding trackId=$trackId to playlist id=${pl.id}, name='${pl.name}'")
            repository.addToPlaylist(pl, trackId)
        }

        logger.info("Finished adding trackId=$trackId to ${playlists.size} playlists")
    }
}