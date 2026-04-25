package com.example.common.messaging.config

import org.springframework.amqp.support.converter.JacksonJsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * Configuration class that defines a bean for the message converter that will be used to serialize and deserialize data.
 * It uses the Jackson library to convert objects to JSON format and vice versa.
 */
@Configuration
class RabbitMQConfig {

    @Bean
    fun jsonMessageConverter(): MessageConverter {
        return JacksonJsonMessageConverter()
    }
}