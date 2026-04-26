package com.example.file_service.application.ports

import com.example.file_service.domain.model.MusicFile
import java.util.UUID

/**
 * Interface for the repository that handles music file storage.
 *
 * We just need to save the file if it passes the validation.
 */
interface MusicFilesRepository {
    fun save(musicFile: MusicFile, path: String)

    fun exists(trackId: UUID) : Boolean

    fun getPath(trackId: UUID) : String?
}