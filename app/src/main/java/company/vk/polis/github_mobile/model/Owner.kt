package company.vk.polis.github_mobile.model

import com.google.gson.annotations.SerializedName

data class Owner(
    val login: String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("html_url")
    val htmlUrl: String
)
