package com.gitficko.github.model

import kotlinx.serialization.Serializable

@Serializable
data class Node(
    val path: String,
    val type: String,
    val sha: String
)
