package paxHumana.veclix.users.domain

import jakarta.persistence.*
import org.jetbrains.annotations.NotNull
import paxHumana.veclix.agentBlocks.domain.AgentBlock
import paxHumana.veclix.common.domain.BaseEntity

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, unique = true)
    val email: String,
    initialUsername: String? = null,
    initialVcoin: Int? = 0,
    initialAvatarUrl: String? = null,
    initialDiscordId: String? = null
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        protected set

    @Column(unique = true)
    var username: String? = initialUsername
        protected set

    @Column(name = "vcoin", nullable = false)
    var vcoin: Int? = initialVcoin
        protected set

    @Column(name = "avatar_url")
    var avatarUrl: String? = initialAvatarUrl
        protected set

    @Column(name = "discord_id", unique = true)
    var discordId: String? = initialDiscordId
        protected set

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL], mappedBy = "crafter")
    protected val mutableCraftedBlocks: MutableList<AgentBlock> = mutableListOf()
    val craftedBlocks: List<AgentBlock> get() = mutableCraftedBlocks.toList()

    fun craftBlock(block: AgentBlock) {
        mutableCraftedBlocks.add(block)
    }

    fun updateMutableData(username: String?, vcoin: Int?, avatarUrl: String?, discordId: String?) {
        username?.let { this.username = it }
        vcoin?.let { this.vcoin = it }
        avatarUrl?.let { this.avatarUrl = it }
        discordId?.let { this.discordId = it }
    }
}