package com.gitficko.github.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class RepositoryDto(
    var id: Int,
    var name: String,
    @SerialName(value = "full_name")
    var fullName: String,
    var owner: User,
    var private: Boolean,
    @SerialName(value = "html_url")
    var htmlUrl: String,
    var description: String?,
    var fork: Boolean,
    var url: String,
    @SerialName(value = "created_at")
    var createdAt: String,
    @SerialName(value = "updated_at")
    var updatedAt: String?,
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
    fun toEntity(): Repository {
        return Repository(
            id,
            name,
            fullName,
            private,
            createdAt,
            updatedAt,
            language,
            description,
            owner.login
        )
    }

    override fun toString(): String {
        return "Repository(id=$id, name='$name', fullName='$fullName', owner=$owner," +
                "private=$private, htmlUrl='$htmlUrl', description=$description, fork=$fork," +
                "url='$url', createdAt='$createdAt', updatedAt='$updatedAt', pushedAt='$pushedAt'," +
                "homepage=$homepage, size=$size, stargazersCount=$stargazersCount," +
                "watchersCount=$watchersCount, language=$language, forksCount=$forksCount," +
                "openIssuesCount=$openIssuesCount, defaultBranch='$defaultBranch')"
    }
}
