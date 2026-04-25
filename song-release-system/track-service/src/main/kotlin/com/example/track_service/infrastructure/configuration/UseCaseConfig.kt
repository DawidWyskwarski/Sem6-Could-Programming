package com.example.track_service.infrastructure.configuration

import com.example.common.messaging.EventPublisher
import com.example.track_service.application.handlers.CreateTrackCommandHandler
import com.example.track_service.application.handlers.MarkTrackAsUploadedCommandHandler
import com.example.track_service.application.ports.TrackRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class to autowire use cases.
 */
@Configuration
class UseCaseConfig {

    @Bean
    fun createTrackCommandHandler(
        repository: TrackRepository
    ): CreateTrackCommandHandler {
       return CreateTrackCommandHandler(repository)
    }

    @Bean
    fun markTrackAsUploadedHandler(
        eventPublisher: EventPublisher,
        repository: TrackRepository
    ) : MarkTrackAsUploadedCommandHandler {
        return MarkTrackAsUploadedCommandHandler(eventPublisher, repository)
    }
}