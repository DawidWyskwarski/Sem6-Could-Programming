package com.example.file_service.application.handlers

import com.example.common.queries.QueryHandler
import com.example.file_service.application.ports.FileStorage
import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.domain.queries.GetTrackMusicFileQuery

class GetTrackMusicFileQueryHandler(
    private val repository: MusicFilesRepository,
    private val fileStorage: FileStorage
): QueryHandler<GetTrackMusicFileQuery, ByteArray> {

    override fun handle(query: GetTrackMusicFileQuery): ByteArray {
        if (!repository.exists(query.trackId)) {
            throw IllegalStateException("No file attached to track with id ${query.trackId}")
        }

        val path = repository.getPath(query.trackId)
            ?: throw IllegalStateException("No file attached to track with id ${query.trackId}")

        return fileStorage.download(path)
    }

}