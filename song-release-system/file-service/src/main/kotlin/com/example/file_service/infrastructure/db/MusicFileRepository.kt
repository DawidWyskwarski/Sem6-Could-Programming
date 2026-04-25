package com.example.file_service.infrastructure.db

import com.example.file_service.application.ports.MusicFilesRepository
import com.example.file_service.domain.model.MusicFile
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Entity
@Table(name = "music_files")
class MusicFileEntity(
    @Id
    val trackId: UUID,
    val path: String
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

    override fun save(musicFile: MusicFile): String {
        logger.info("Attempting to save music file for trackId={}", musicFile.trackId)

        val path = "/"

        // Save to DB
        val entity = MusicFileEntity(trackId = musicFile.trackId, path = path)
        springDataRepository.save(entity)

        logger.info("Saved music file to path={}", path)
        return path
    }
}