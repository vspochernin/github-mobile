package company.vk.polis.github_mobile.model

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryRootNode(
    val sha: String,
    val tree: List<Node>
)
