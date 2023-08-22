package paxHumana.veclix.users.service

import com.querydsl.jpa.impl.JPAQueryFactory
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import paxHumana.veclix.users.controller.UserRequestDto
import paxHumana.veclix.users.domain.QUser
import paxHumana.veclix.users.domain.User
import paxHumana.veclix.users.repository.UserRepository
import java.time.LocalDateTime


@Service
class UserService (
    private val userRepository: UserRepository,
    private val jpaQueryFactory: JPAQueryFactory,
) {
    @Transactional
    fun create(request: UserRequestDto?) : UserResponse {
        if (userRepository.existsByEmail(request?.email
                ?: throw IllegalArgumentException("invalid request"))) {
            throw IllegalArgumentException("User with email ${request.email} already exists")
        }
        val user = User(
            email = request.email,
            username = request.username,
            avatarUrl = request.avatarUrl,
            discordId = request.discordId
        )
        return UserResponse.of(userRepository.save(user))
    }

    @Transactional
    fun update(id: Long, requestDto: UserRequestDto): UserResponse {
        val user = userRepository.findById(id)
            .orElse(null) ?: throw IllegalArgumentException("해당 유저가 없습니다. id=$id")

        user.updateMutableData(
            username = requestDto.username,
            vcoin = requestDto.vcoin, // Assuming vcoin is part of UserRequestDto, otherwise you might need to adjust this
            avatarUrl = requestDto.avatarUrl,
            discordId = requestDto.discordId
        )
        return UserResponse.of(user)
    }

    fun findById(id: Long): UserResponse {
        val user = userRepository.findById(id)
            .orElse(null) ?: throw IllegalArgumentException("해당 유저가 없습니다. id=$id")
        return UserResponse.of(user)
    }

    fun findAllDesc(): List<UserResponse> {
        return userRepository.findAllByOrderByUpdatedAtDesc()
            .map { UserResponse.of(it) }
    }

    fun findByEmail(email: String): UserResponse? {
       val user = userRepository.findByEmail(email)
           ?: throw IllegalArgumentException("해당 email을 가진 유저가 없습니다. email:$email")
        return UserResponse.of(user)
    }

    fun findByEmailAndUsername(email: String?, username: String?, discordId: String?): List<User> {
        val qUser = QUser.user

        return jpaQueryFactory.selectFrom(qUser)
            .where(
                email?.let { qUser.email.eq(it) },
                username?.let { qUser.username.eq(it) },
                discordId?.let { qUser.discordId.eq(it) }
            )
            .fetch()
    }

    @Transactional
    fun delete(id: Long): Long {
        val user = userRepository.findById(id)
            .orElse(null) ?: throw IllegalArgumentException("해당 유저가 없습니다. id=$id")
        userRepository.delete(user)
        return id
    }
}



data class UserResponse(
    val id: Long,
    val email: String,
    val username: String?,
    val avatarUrl: String?,
    val discordId: String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime?,
) {

    companion object {
        fun of(user: User): UserResponse {
            checkNotNull(user.id)

            return UserResponse(
                id = user.id!!,
                email = user.email,
                username = user.username,
                createdAt = user.createdAt,
                avatarUrl = user.avatarUrl,
                discordId = user.discordId,
                updatedAt = user.updatedAt
            )
        }
    }

}