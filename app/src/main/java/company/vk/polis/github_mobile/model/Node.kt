package company.vk.polis.github_mobile.model

import kotlinx.serialization.Serializable

@Serializable
data class Node(
    val path: String,
    val type: String,
    val sha: String
)
