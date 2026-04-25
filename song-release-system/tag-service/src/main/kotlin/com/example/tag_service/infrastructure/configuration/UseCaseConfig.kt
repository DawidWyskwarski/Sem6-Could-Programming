package com.example.tag_service.infrastructure.configuration

import com.example.common.messaging.EventPublisher
import com.example.tag_service.application.handlers.AssignTagsCommandHandler
import com.example.tag_service.application.ports.TagRepository
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class to autowire use cases.
 */
@Configuration
class UseCaseConfig {

    @Bean
    fun assignTagsUseCase(
        eventPublisher: EventPublisher,
        repository: TagRepository,
    ): AssignTagsCommandHandler {
        return AssignTagsCommandHandler(repository, eventPublisher)
    }
}