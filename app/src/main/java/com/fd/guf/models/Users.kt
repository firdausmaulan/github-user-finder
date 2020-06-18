package com.fd.guf.models

import com.google.gson.annotations.SerializedName

data class Users(
    @SerializedName("total_count")
    val totalCount: Int,
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean,
    @SerializedName("items")
    val items: List<User>
)