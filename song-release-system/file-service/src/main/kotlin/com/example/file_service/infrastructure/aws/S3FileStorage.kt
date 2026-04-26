package com.example.file_service.infrastructure.aws

import com.example.file_service.application.ports.FileStorage
import com.example.file_service.domain.model.MusicFile
import org.apache.tika.Tika
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.model.GetObjectRequest
import software.amazon.awssdk.services.s3.model.PutObjectRequest
import java.io.File
import java.util.UUID

@Component
class S3FileStorage(
    private val s3Client: S3Client,
    @Value("\${aws.s3.bucket}") val bucketName: String,
): FileStorage {

    private val tika = Tika()

    override fun store(mf: MusicFile): String {
        val key = "tracks/${mf.trackId}/${mf.file.name}"

        val request = PutObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .contentType(resolveMimeType(mf.file))
            .build()

        s3Client.putObject(request, mf.file.toPath())

        return key
    }

    override fun download(s3Uri: String): ByteArray {

        val request = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(s3Uri)
            .build()

        return s3Client.getObject(request).readAllBytes()
    }

    private fun resolveMimeType(file: File): String = tika.detect(file)
}