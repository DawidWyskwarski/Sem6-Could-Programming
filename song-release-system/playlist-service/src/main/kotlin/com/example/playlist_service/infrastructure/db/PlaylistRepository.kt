package com.example.playlist_service.infrastructure.db

import com.example.playlist_service.application.ports.PlaylistRepository
import com.example.playlist_service.domain.model.Playlist
import jakarta.persistence.*
import org.slf4j.LoggerFactory
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Entity
@Table(name = "global_playlists")
class GlobalPlaylistEntity(
    @Column(nullable = false)
    val name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)

@Entity
@Table(name = "playlist_tags")
class PlaylistTagEntity(
    @Column(nullable = false)
    val playlistId: UUID,

    @Column(nullable = false)
    val tagId: UUID,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)

@Entity
@Table(name = "playlist_tracks")
class PlaylistTrackEntity(
    @Column(nullable = false)
    val playlistId: UUID,

    @Column(nullable = false)
    val trackId: UUID,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)

interface GlobalPlaylistSpringDataRepository : CrudRepository<GlobalPlaylistEntity, UUID>
interface PlaylistTagSpringDataRepository : CrudRepository<PlaylistTagEntity, UUID> {
    fun findByPlaylistId(playlistId: UUID): List<PlaylistTagEntity>
}
interface PlaylistTrackSpringDataRepository : CrudRepository<PlaylistTrackEntity, UUID>

@Repository
class PlaylistRepository(
    private val playlistRepo: GlobalPlaylistSpringDataRepository,
    private val tagRepo: PlaylistTagSpringDataRepository,
    private val trackRepo: PlaylistTrackSpringDataRepository
) : PlaylistRepository {

    private val logger = LoggerFactory.getLogger(PlaylistRepository::class.java)

    override fun findPlaylistsWithMinimumCommonTags(
        tagIds: Set<UUID>,
        minCommon: Int
    ): List<Playlist> {
        logger.info("Finding playlists with minimum {} common tags out of {} provided tags", minCommon, tagIds.size)
        val allPlaylists = playlistRepo.findAll()
        val allTags = tagRepo.findAll()

        // Group tags by playlist ID
        val playlistTagsMap = allTags.groupBy { it.playlistId }.mapValues { entry -> entry.value.map { it.tagId } }

        return allPlaylists.filter { playlist ->
            val tagsForPlaylist = playlistTagsMap[playlist.id!!]?.toSet() ?: emptySet()
            val commonTagsCount = tagsForPlaylist.intersect(tagIds).size
            commonTagsCount >= minCommon
        }.map { playlist ->
            Playlist(
                id = playlist.id!!,
                name = playlist.name,
                tagIds = playlistTagsMap[playlist.id!!] ?: emptyList()
            )
        }
    }

    override fun addToPlaylist(playlist: Playlist, trackId: UUID) {
        logger.info("Saving trackId={} to playlistId={}", trackId, playlist.id)
        val entity = PlaylistTrackEntity(playlistId = playlist.id, trackId = trackId)
        trackRepo.save(entity)
    }
}