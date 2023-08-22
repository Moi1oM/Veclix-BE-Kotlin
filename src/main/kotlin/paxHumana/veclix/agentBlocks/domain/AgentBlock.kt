package paxHumana.veclix.agentBlocks.domain

import jakarta.persistence.*
import paxHumana.veclix.common.domain.BaseEntity
import paxHumana.veclix.users.domain.User
import java.util.*

@Entity
@Table(name = "agent_blocks")
class AgentBlock(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false)
    var id: UUID? = UUID.randomUUID(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "crafter")
    val crafter: User,

    @Column(name = "properties", columnDefinition = "JSON")
    var properties: String? = null,

    @Column(name = "contents")
    var contents: String? = null,

    @Column(name = "parent")
    var parent: UUID? = null,

    var detail: String = "no detail",

    var stars: Float? = null,

    var description: String = "no description",

    val price: String = "10000",

    @Column(name = "in_store")
    val inStore: Boolean = false,
) : BaseEntity()