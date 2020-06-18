package com.fd.guf.models

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("total_count")
    val totalCount: Int = 0,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean = false,
    @SerializedName("items")
    val items: List<User> = ArrayList()
)