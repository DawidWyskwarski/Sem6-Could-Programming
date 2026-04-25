package com.example.track_service

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackages = ["com.example.track_service", "com.example.common"])
class TrackServiceApplication

/**
 * Track Service: Core system component for initial track registration and metadata validation.
 * - Enforces domain rules (title <= 100 chars, max 20 keywords) via CreateTrackUseCase.
 * - Stores UUID-based track records with an initial PENDING status.
 * - Listens for FileAttachedEvent (from file-service) to transition track status to UPLOADED.
 * - Emits TrackUploadedEvent for downstream processing (e.g., tagging, notifications).
 */
fun main(args: Array<String>) {
	runApplication<TrackServiceApplication>(*args)
}
