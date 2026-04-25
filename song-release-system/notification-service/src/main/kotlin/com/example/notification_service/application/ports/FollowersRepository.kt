package com.example.notification_service.application.ports

import com.example.notification_service.domain.model.User
import java.util.UUID

/**
 * Port interface for Followers repository operations.
 * This interface defines the contract for retrieving Artist's followers.
 */
interface FollowersRepository {
    fun getArtistFollowers(artistId: UUID): List<User>
}