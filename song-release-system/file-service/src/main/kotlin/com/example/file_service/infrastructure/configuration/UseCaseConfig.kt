package com.example.file_service.infrastructure.configuration

import com.example.common.messaging.EventPublisher
import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.application.usecases.AttachFileToTrackUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class to autowire use cases.
 */
@Configuration
class UseCaseConfig {

    @Bean
    fun attachFileToTrackUseCase(
        eventPublisher: EventPublisher,
        musicFileRepository: MusicFilesRepository
    ) : AttachFileToTrackUseCase {

        return AttachFileToTrackUseCase(
            eventPublisher = eventPublisher,
            repository = musicFileRepository
        )
    }
}