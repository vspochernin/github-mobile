package company.vk.polis.github_mobile.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Repository(
    val id: Int,
    val name: String,
    @SerialName(value = "full_name")
    val fullName: String,
    val owner: Owner,
    val private: Boolean,
    @SerialName(value = "html_url")
    val htmlUrl: String,
    val description: String?,
    val fork: Boolean,
    val url: String,
    @SerialName(value = "created_at")
    val createdAt: String,
    @SerialName(value = "updated_at")
    val updatedAt: String,
    @SerialName(value = "pushed_at")
    val pushedAt: String,
    val homepage: String?,
    val size: Int,
    @SerialName(value = "stargazers_count")
    val stargazersCount: Int,
    @SerialName(value = "watchers_count")
    val watchersCount: Int,
    val language: String?,
    @SerialName(value = "forks_count")
    val forksCount: Int,
    @SerialName(value = "open_issues_count")
    val openIssuesCount: Int,
    @SerialName(value = "default_branch")
    val defaultBranch: String
)
