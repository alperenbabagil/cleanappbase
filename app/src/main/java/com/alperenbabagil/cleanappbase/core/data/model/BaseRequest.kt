package com.alperenbabagil.cleanappbase.core.data.model

import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.google.gson.annotations.SerializedName

data class BaseRequest<T>(
    @SerializedName("data") val load: T,
    @SerializedName("token") val token: String = CoreDataConstants.TOKEN,
    @SerializedName("parameters") var parameters: Parameters?= Parameters()
)