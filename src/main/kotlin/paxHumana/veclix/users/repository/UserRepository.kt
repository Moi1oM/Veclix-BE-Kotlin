package paxHumana.veclix.users.repository

import org.springframework.data.jpa.repository.JpaRepository
import paxHumana.veclix.users.domain.User

interface UserRepository: JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun findAllByOrderByUpdatedAtDesc() : List<User>
}