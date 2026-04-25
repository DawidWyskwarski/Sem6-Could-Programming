package com.example.track_service.infrastructure.db

import com.example.track_service.domain.model.Track
import com.example.track_service.domain.model.TrackStatus
import jakarta.persistence.*
import java.util.UUID


@Entity
@Table(name = "tracks")
class TrackEntity(
    @Id
    val id: UUID,

    @Column(nullable = false)
    val artistId: UUID,

    @Column(nullable = false, length = 100)
    val title: String,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val status: TrackStatus,

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "keywords", joinColumns = [JoinColumn(name = "track_id")])
    @Column(name = "keyword")
    val keywords: List<String>
) {
    fun toDomain(): Track {
        return Track(
            id = id,
            artistId = artistId,
            title = title,
            keywords = keywords,
            status = status
        )
    }

    companion object {
        fun fromDomain(track: Track): TrackEntity {
            return TrackEntity(
                id = track.id,
                artistId = track.artistId,
                title = track.title,
                status = track.status,
                keywords = track.keywords
            )
        }
    }
}

