package com.gitficko.github.model

import kotlinx.serialization.Serializable

@Serializable
data class RepositoryRootNode(
    val sha: String,
    val tree: List<Node>
)
