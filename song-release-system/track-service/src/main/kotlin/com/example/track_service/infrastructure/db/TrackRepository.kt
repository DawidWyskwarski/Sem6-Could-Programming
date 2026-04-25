package com.example.track_service.infrastructure.db

import com.example.track_service.application.ports.TrackRepository
import com.example.track_service.domain.model.Track
import org.slf4j.LoggerFactory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SpringDataTrackRepository : JpaRepository<TrackEntity, UUID>

/**
 * Implementation of the TrackRepository interface using Spring Data JPA.
 * This class serves as the bridge between the domain model and the database layer.
 */
@Repository
class TrackRepository(
    private val springDataTrackRepository: SpringDataTrackRepository
): TrackRepository {

    private val logger = LoggerFactory.getLogger(TrackRepository::class.java)

    override fun save(track: Track) {
        logger.info("Attempting to save track={}", track.id)
        val entity = TrackEntity.fromDomain(track)
        springDataTrackRepository.save(entity)
    }

    override fun findById(id: UUID): Track? {
        logger.info("Attempting to find track by id={}", id)
        val entity = springDataTrackRepository.findById(id).orElse(null)
        return entity?.toDomain()
    }
}