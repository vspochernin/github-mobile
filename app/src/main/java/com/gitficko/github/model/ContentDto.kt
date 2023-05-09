package com.gitficko.github.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
data class ContentDto(
    val name: String,
    val path: String,
    val sha: String,
    val size: Int,
    val url: String,
    val html_url: String,
    val git_url: String,
    val download_url: String?,
    val type: String,
    @SerialName("_links")
    val links: Links
)

@kotlinx.serialization.Serializable
data class Links(
    val git: String,
    val html: String,
    val self: String
)
