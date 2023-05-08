package com.gitficko.github.model

import kotlinx.serialization.Serializable

@Serializable
open class User (
    var id: Int,
    var login: String,
)
