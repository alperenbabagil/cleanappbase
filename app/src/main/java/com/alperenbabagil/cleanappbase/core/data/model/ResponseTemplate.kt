package com.alperenbabagil.cleanappbase.core.data.model


import com.alperenbabagil.dataholder.CoreDomainConstants
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.google.gson.annotations.SerializedName

data class ResponseTemplate<T>(
    @SerializedName("data")
    var `data`: T?=null,
    @SerializedName("error")
    var error: Error?=null,
    @SerializedName("status")
    var status: String?=CoreDataConstants.SERVER_STATUS_SUCCESS
)