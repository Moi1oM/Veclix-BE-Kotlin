package paxHumana.veclix.users.domain

import jakarta.persistence.*
import paxHumana.veclix.agentBlocks.domain.AgentBlock
import paxHumana.veclix.common.domain.BaseEntity

@Entity
@Table(name = "users")
class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false, unique = true)
    val email: String,

    @Column(unique = true)
    var username: String? = null,

    @Column(name = "vcoin", nullable = false)
    var vcoin: Int? = 0,

    @Column(name = "avatar_url")
    var avatarUrl: String? = null,

    @Column(name = "discord_id", unique = true)
    var discordId: String? = null
) : BaseEntity() {

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