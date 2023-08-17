package paxHumana.veclix.common.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@MappedSuperclass
open class BaseEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
}
