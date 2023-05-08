package com.gitficko.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class PullRequestsInfo (
    @SerialName("total_count")
    val totalCount: Int,
    val items: List<PullRequestDto>
)
