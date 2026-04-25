package com.example.track_service.infrastructure.configuration

import com.example.common.messaging.EventPublisher
import com.example.track_service.application.ports.TrackRepository
import com.example.track_service.application.usecases.CreateTrackUseCase
import com.example.track_service.application.usecases.OnFileAttachedUseCase
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class to autowire use cases.
 */
@Configuration
class UseCaseConfig {

    @Bean
    fun createTrackUseCase(
        repository: TrackRepository
    ): CreateTrackUseCase {
       return CreateTrackUseCase(repository)
    }

    @Bean
    fun onFileAttachedUseCase(
        eventPublisher: EventPublisher,
        repository: TrackRepository
    ) : OnFileAttachedUseCase {
        return OnFileAttachedUseCase(eventPublisher, repository)
    }
}