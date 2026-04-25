package com.example.file_service.domain.model

import com.example.file_service.domain.exception.EmptyFileException
import com.example.file_service.domain.exception.InvalidFileExtensionException
import com.example.file_service.domain.exception.InvalidFileSizeException
import java.util.UUID

/**
 * Represents a music file attached to a track. It includes validation for file content, size, and extension.
 */
class MusicFile(
    val trackId: UUID,
    val fileName: String,
    val file: ByteArray,
) {
    init {
        if (file.isEmpty()) {
            throw EmptyFileException("File content cannot be empty")
        }

        val maxFileSize = 50 * 1024 * 1024 // 50 MB
        if (file.size > maxFileSize) {
            throw InvalidFileSizeException("File size cannot exceed 50 MB")
        }

        // Only accepts popular audio formats.
        val allowedExtensions = listOf(".mp3", ".wav", ".flac", ".aac", ".ogg")
        if (allowedExtensions.none { fileName.lowercase().endsWith(it) }) {
            throw InvalidFileExtensionException("File extension must be one of: ${allowedExtensions.joinToString(", ")}")
        }
    }
}
