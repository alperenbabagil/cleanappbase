package com.alperenbabagil.cleanappbase.core.data.model

import com.alperenbabagil.dataholder.BaseError
import com.google.gson.annotations.SerializedName

data class ServerError(
    @SerializedName("errorMessage") val errorMessage: String,
    @SerializedName("errorCode") val errorCode: Int
) : BaseError()