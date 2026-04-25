package com.example.tag_service.infrastructure.db

import com.example.tag_service.application.ports.TagRepository
import com.example.tag_service.domain.model.Tag
import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.slf4j.LoggerFactory
import java.util.UUID

@Entity
@Table(name = "tags")
class TagEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID(),

    @Column(unique = true, nullable = false)
    val name: String
)

@Entity
@Table(name = "track_tags")
class TrackTagEntity(
    @Column(nullable = false)
    val trackId: UUID,

    @Column(nullable = false)
    val tagId: UUID,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)

interface SpringDataTagRepository : CrudRepository<TagEntity, UUID> {
    fun findByNameIn(names: List<String>): List<TagEntity>
}

interface SpringDataTrackTagRepository : CrudRepository<TrackTagEntity, UUID>

@Repository
class TagRepository(
    private val springDataTagRepository: SpringDataTagRepository,
    private val springDataTrackTagRepository: SpringDataTrackTagRepository
) : TagRepository {

    private val logger = LoggerFactory.getLogger(TagRepository::class.java)

    override fun getTagsByNames(names: List<String>): List<Tag> {
        logger.info("Fetching tags by names: {}", names)
        val entities = springDataTagRepository.findByNameIn(names)
        return entities.map { Tag(it.id, it.name) }
    }

    override fun assignTags(
        tag: List<Tag>,
        trackId: UUID
    ) {
        logger.info("Assigning {} tags to trackId={}", tag.size, trackId)
        val entities = tag.map {
            TrackTagEntity(trackId = trackId, tagId = it.id)
        }
        springDataTrackTagRepository.saveAll(entities)
    }
}