package com.alperenbabagil.cleanappbase.core.data.model

import com.alperenbabagil.cabdomain.model.BaseError
import com.google.gson.annotations.SerializedName

data class ServerError(
    @SerializedName("errorMessage") val errorMessage: String,
    @SerializedName("errorCode") val errorCode: Int
) : BaseError