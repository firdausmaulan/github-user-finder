package com.fd.guf.models

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("login")
    val login: String = "",
    @SerializedName("avatar_url")
    val avatarUrl: String = "",
    @SerializedName("html_url")
    val htmlUrl: String = ""
)