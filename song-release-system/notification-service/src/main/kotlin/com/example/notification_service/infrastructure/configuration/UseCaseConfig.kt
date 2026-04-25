package com.example.notification_service.infrastructure.configuration

import com.example.notification_service.application.ports.FollowersRepository
import com.example.notification_service.application.handlers.NotifyFollowersCommandHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class to autowire use cases.
 */
@Configuration
class UseCaseConfig {

    @Bean
    fun notifyFollowersUseCase(
        repository: FollowersRepository
    ) : NotifyFollowersCommandHandler {
        return NotifyFollowersCommandHandler(repository)
    }
}