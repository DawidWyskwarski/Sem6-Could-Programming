package com.example.file_service.infrastructure.configuration

import com.example.common.messaging.EventPublisher
import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.application.handlers.AttachFileToTrackCommandHandler
import com.example.file_service.application.handlers.GetTrackMusicFileQueryHandler
import com.example.file_service.application.ports.FileStorage
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class to autowire use cases.
 */
@Configuration
class UseCaseConfig {

    @Bean
    fun attachFileToTrackCommandHandler(
        eventPublisher: EventPublisher,
        fileStorage: FileStorage,
        musicFileRepository: MusicFilesRepository
    ): AttachFileToTrackCommandHandler {

        return AttachFileToTrackCommandHandler(
            eventPublisher = eventPublisher,
            fileStorage = fileStorage,
            repository = musicFileRepository
        )
    }

    @Bean
    fun getTrackMusicFileQueryHandler(
        musicFilesRepository: MusicFilesRepository,
        fileStorage: FileStorage,
    ): GetTrackMusicFileQueryHandler {
        return GetTrackMusicFileQueryHandler(
            repository = musicFilesRepository,
            fileStorage = fileStorage
        )
    }
}