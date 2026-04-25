package com.example.notification_service.application.handlers

import com.example.common.commands.CommandHandler
import com.example.notification_service.application.ports.FollowersRepository
import com.example.notification_service.domain.commands.NotifyFollowersCommand
import com.example.notification_service.domain.model.User
import org.slf4j.LoggerFactory

class NotifyFollowersCommandHandler(
    private val followersRepository: FollowersRepository
) : CommandHandler<NotifyFollowersCommand, Unit> {

    private val logger = LoggerFactory.getLogger(NotifyFollowersCommandHandler::class.java)

    override fun handle(command: NotifyFollowersCommand) {
        logger.info("Executing NotifyFollowersUseCase for artistId=${command.artistId} and trackId=${command.trackId}")

        val followers: List<User> = followersRepository.getArtistFollowers(command.artistId)

        logger.info("Found ${followers.size} followers to notify", )

        notifyUsers(followers, command.trackTitle)
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