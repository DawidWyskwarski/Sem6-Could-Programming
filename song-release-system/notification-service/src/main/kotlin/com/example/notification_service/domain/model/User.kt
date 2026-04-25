package com.example.notification_service.domain.model

import java.util.UUID

/**
 * Represents a user within the notification domain.
 *
 * This model holds the essential information required to identify a user
 * and deliver notifications to them.
 *
 * @property userId Unique identifier for the user.
 * @property username The user's display or login name.
 * @property email The user's email address, used as a primary contact for notifications.
 */
data class User(
    val userId: UUID,
    val username: String,
    val email: String,
)