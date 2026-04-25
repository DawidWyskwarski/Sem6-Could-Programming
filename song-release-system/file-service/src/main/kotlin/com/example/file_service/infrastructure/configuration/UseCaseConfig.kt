package com.example.file_service.infrastructure.configuration

import com.example.common.messaging.EventPublisher
import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.application.handlers.AttachFileToTrackCommandHandler
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
        musicFileRepository: MusicFilesRepository
    ) : AttachFileToTrackCommandHandler {

        return AttachFileToTrackCommandHandler(
            eventPublisher = eventPublisher,
            repository = musicFileRepository
        )
    }
}