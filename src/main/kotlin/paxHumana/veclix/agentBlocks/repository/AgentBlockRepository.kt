package paxHumana.veclix.agentBlocks.repository

import org.springframework.data.jpa.repository.JpaRepository
import paxHumana.veclix.agentBlocks.domain.AgentBlock
import java.util.UUID

interface AgentBlockRepository : JpaRepository<AgentBlock, UUID>{
}