package com.example.file_service.application.ports

import com.example.file_service.domain.model.MusicFile

/**
 * Interface for the repository that handles music file storage.
 *
 * We just need to save the file if it passes the validation.
 */
interface MusicFilesRepository {
    fun save(musicFile: MusicFile): String
}