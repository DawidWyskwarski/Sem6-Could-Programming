package com.example.playlist_service.infrastructure.configuration

import com.example.playlist_service.application.ports.PlaylistRepository
import com.example.playlist_service.application.handlers.AddTrackToPlaylistCommandHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class to autowire use cases.
 */
@Configuration
class UseCaseConfig {

    @Bean
    fun addToPlaylistUseCase(
        repository: PlaylistRepository
    ): AddTrackToPlaylistCommandHandler {
        return AddTrackToPlaylistCommandHandler(repository)
    }
}