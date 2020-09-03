package com.alperenbabagil.cleanappbase.core.data.model


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("errorCode")
    val errorCode: Int?,
    @SerializedName("errorMessage")
    val errorMessage: String?
)