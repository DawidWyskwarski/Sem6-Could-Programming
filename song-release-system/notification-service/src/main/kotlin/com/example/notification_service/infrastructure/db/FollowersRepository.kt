package com.example.notification_service.infrastructure.db

import com.example.notification_service.application.ports.FollowersRepository
import com.example.notification_service.domain.model.User
import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.slf4j.LoggerFactory
import java.util.UUID

@Entity
@Table(name = "users")
class UserEntity(
    @Column(nullable = false, unique = true)
    val email: String,

    @Column(nullable = false)
    val name: String,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)

@Entity
@Table(name = "followers")
class FollowerEntity(
    @Column(nullable = false)
    val userId: UUID,

    @Column(nullable = false)
    val artistId: UUID,

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null
)

interface UserSpringDataRepository : CrudRepository<UserEntity, UUID>
interface FollowerSpringDataRepository : CrudRepository<FollowerEntity, UUID> {
    fun findByArtistId(artistId: UUID): List<FollowerEntity>
}

@Repository
class FollowersRepository(
    private val userRepository: UserSpringDataRepository,
    private val followerRepository: FollowerSpringDataRepository
) : FollowersRepository {

    private val logger = LoggerFactory.getLogger(FollowersRepository::class.java)

    override fun getArtistFollowers(artistId: UUID): List<User> {
        logger.info("Fetching followers for artistId: {}", artistId)
        val followers = followerRepository.findByArtistId(artistId)
        val userIds = followers.map { it.userId }
        val users = userRepository.findAllById(userIds)
        return users.map {
            User(userId = it.id!!, username = it.name, email = it.email)
        }
    }
}