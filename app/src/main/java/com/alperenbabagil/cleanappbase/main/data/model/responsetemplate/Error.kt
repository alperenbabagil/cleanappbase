package com.alperenbabagil.cleanappbase.main.data.model.responsetemplate


import com.google.gson.annotations.SerializedName

data class Error(
    @SerializedName("errorCode")
    val errorCode: Int?,
    @SerializedName("errorMessage")
    val errorMessage: String?
)