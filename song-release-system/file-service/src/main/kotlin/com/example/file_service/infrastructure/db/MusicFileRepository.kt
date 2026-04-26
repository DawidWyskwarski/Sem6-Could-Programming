package com.example.file_service.infrastructure.db

import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.domain.model.MusicFile
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.Instant
import java.util.*

@Entity
@Table(name = "music_files")
class MusicFileEntity(
    @Id
    val trackId: UUID,
    val fileName: String,
    val fileExtension: String,
    val fileSize: Long,
    val path: String,
    val fileCreatedAt: Instant = Instant.now(),
)

interface SpringDataMusicFileRepository : CrudRepository<MusicFileEntity, UUID>

/**
 * Implementation of a MusicFilesRepository that uses Spring Data JPA to persist music file information in a relational database.
 * This class serves as the bridge between the domain model and the database layer.
 */
@Repository
class MusicFileRepository(
    private val springDataRepository: SpringDataMusicFileRepository
) : MusicFilesRepository {

    private val logger = LoggerFactory.getLogger(MusicFileRepository::class.java)

    override fun save(musicFile: MusicFile, path: String) {
        logger.info("Attempting to save music file for trackId=${musicFile.trackId}")

        // Save to DB
        val entity = MusicFileEntity(
            trackId = musicFile.trackId,
            fileName = musicFile.file.name,
            fileExtension = musicFile.file.extension,
            fileSize = musicFile.file.length(),
            path = path
        )

        springDataRepository.save(entity)

        logger.info("Music file successfully saved")
    }

    override fun exists(trackId: UUID): Boolean {
        logger.info("Checking if a music file already exists for trackId={}", trackId)

        return springDataRepository.existsById(trackId)
    }

    override fun getPath(trackId: UUID): String? {
        logger.info("Retrieving file path for trackId={}", trackId)

        val entity = springDataRepository.findById(trackId)
        if (entity.isEmpty) {
            logger.warn("No music file found for trackId={}", trackId)
            return null
        }

        val path = entity.get().path
        logger.info("Found file path for trackId={}: {}", trackId, path)
        return path
    }
}