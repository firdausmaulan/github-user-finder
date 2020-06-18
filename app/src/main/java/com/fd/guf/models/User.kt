package com.fd.guf.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
)