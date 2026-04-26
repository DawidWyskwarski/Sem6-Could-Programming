package com.example.file_service.application.ports

import com.example.file_service.domain.model.MusicFile
import software.amazon.awssdk.services.s3.model.S3Object
import java.util.UUID

interface FileStorage {
    fun store(mf: MusicFile): String

    fun download(s3Uri: String): ByteArray
}