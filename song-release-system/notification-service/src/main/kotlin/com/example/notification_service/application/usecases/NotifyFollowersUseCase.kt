package com.example.notification_service.application.usecases

import com.example.notification_service.application.ports.FollowersRepository
import com.example.notification_service.domain.model.User
import org.slf4j.LoggerFactory
import java.util.UUID

/**
 * Use case responsible for notifying an artist's followers about a new track release.
 *
 * This class retrieves the list of users following a specific artist and triggers
 * a notification process (e.g., sending an email) for each follower.
 *
 * @property followersRepository The repository used to fetch the artist's followers.
 */
class NotifyFollowersUseCase(
    private val followersRepository: FollowersRepository
) {

    private val logger = LoggerFactory.getLogger(NotifyFollowersUseCase::class.java)

    fun execute(
        artistId: UUID,
        trackId: UUID,
        trackTitle: String
    ) {
        logger.info("Executing NotifyFollowersUseCase for artistId={} and trackId={}", artistId, trackId)
        val followers: List<User> = followersRepository.getArtistFollowers(artistId)

        logger.info("Found {} followers to notify", followers.size)
        notifyUsers(followers, trackTitle)
    }

    /**
     * Simulates sending notifications to the provided list of users.
     *
     * @param users The list of followers to be notified.
     * @param trackTitle The title of the track to include in the notification message.
     */
    private fun notifyUsers(users: List<User>, trackTitle: String) {
        users.forEach { user ->
            logger.info("Sending email to {}: Artist uploaded a new track '{}'!", user.email, trackTitle)
        }
    }
}