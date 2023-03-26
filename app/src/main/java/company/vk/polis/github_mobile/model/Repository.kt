package company.vk.polis.github_mobile.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Entity(tableName = "repository")
@Serializable
class Repository(
    @PrimaryKey
    var id: Int,
    var name: String,
    @SerialName(value = "full_name")
    var fullName: String,
    var owner: Owner,
    var private: Boolean,
    @SerialName(value = "html_url")
    var htmlUrl: String,
    var description: String?,
    var fork: Boolean,
    var url: String,
    @SerialName(value = "created_at")
    var createdAt: String,
    @SerialName(value = "updated_at")
    var updatedAt: String,
    @SerialName(value = "pushed_at")
    var pushedAt: String,
    var homepage: String?,
    var size: Int,
    @SerialName(value = "stargazers_count")
    var stargazersCount: Int,
    @SerialName(value = "watchers_count")
    var watchersCount: Int,
    var language: String?,
    @SerialName(value = "forks_count")
    var forksCount: Int,
    @SerialName(value = "open_issues_count")
    var openIssuesCount: Int,
    @SerialName(value = "default_branch")
    var defaultBranch: String
) {
    // Пустой конструктор для Room.
    constructor() : this(
        id = 0,
        name = "",
        fullName = "",
        owner = Owner(),
        private = false,
        htmlUrl = "",
        description = null,
        fork = false,
        url = "",
        createdAt = "",
        updatedAt = "",
        pushedAt = "",
        homepage = null,
        size = 0,
        stargazersCount = 0,
        watchersCount = 0,
        language = null,
        forksCount = 0,
        openIssuesCount = 0,
        defaultBranch = ""
    )
}
