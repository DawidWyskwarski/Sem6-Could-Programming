package com.example.file_service

import io.awspring.cloud.autoconfigure.core.CredentialsProviderAutoConfiguration
import io.awspring.cloud.autoconfigure.core.RegionProviderAutoConfiguration
import io.awspring.cloud.autoconfigure.s3.S3AutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
	scanBasePackages = ["com.example.file_service", "com.example.common"]
)
class FileServiceApplication

/**
 * File Service: Responsible for handling physical audio file uploads and storage.
 * - Enforces file restrictions (max 50 MB, allowed formats: .mp3, .wav, .flac, .aac, .ogg).
 * - Processes uploads via AttachFileToTrackUseCase, linking raw audio bytes to an "existing" Track ID.
 * - Manages actual byte persistence via MusicFilesRepository.
 * - Emits FileAttachedEvent upon successful upload, which signals downstream services (like track-service)
 *   that the audio payload is ready.
 */
fun main(args: Array<String>) {
	runApplication<FileServiceApplication>(*args)
}
