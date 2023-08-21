package paxHumana.veclix.users.repository

import org.springframework.data.jpa.repository.JpaRepository
import paxHumana.veclix.users.domain.User

interface UserRepository: JpaRepository<User, Long> {
}