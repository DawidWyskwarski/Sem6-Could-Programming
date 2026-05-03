package com.example.notification_service

import io.github.cdimascio.dotenv.dotenv
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/**
 * Main application class for Notification Service.
 *
 * This service is responsible for alerting users about system events.
 * For instance, it listens for track release events and notifies the
 * followers of the respective artists about the newly uploaded tracks.
 */
@SpringBootApplication(scanBasePackages = ["com.example.notification_service", "com.example.common"])
class NotificationServiceApplication

fun main(args: Array<String>) {

	val dotenv = dotenv {
		directory = "./notification-service"
		ignoreIfMissing = true
	}

	dotenv.entries().forEach { entry ->
		System.setProperty(entry.key, entry.value)
	}

	runApplication<NotificationServiceApplication>(*args)
}
