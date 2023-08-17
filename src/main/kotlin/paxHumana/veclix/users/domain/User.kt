package paxHumana.veclix.users.domain

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import paxHumana.veclix.common.domain.BaseEntity

@Entity
@Table(name = "users")
class User : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null

    @NotNull
    @Column(unique = true)
    lateinit var email: String

    @NotNull
    @Column(unique = true)
    lateinit var username: String

    @Column(name = "vcoin", nullable = false)
    var vcoin: Int = 0

    @Column(name = "avatar_url")
    var avatarUrl: String? = null

    @NotNull
    @Column(name = "discord_id", unique = true)
    lateinit var discordId: String

//    @OneToMany(mappedBy = "crafterUser")
//    var agentBlocks: Set<AgentBlock> = HashSet()
//
//    @OneToMany(mappedBy = "crafterUser")
//    var taskBlocks: Set<TaskBlock> = HashSet()
//
//    @OneToMany(mappedBy = "crafterUser")
//    var toolBlocks: Set<ToolBlock> = HashSet()
//
//    @OneToMany(mappedBy = "user")
//    var reviews: Set<Review> = HashSet()
//
//    @OneToMany(mappedBy = "user")
//    var scraps: Set<UserScrap> = HashSet()
}