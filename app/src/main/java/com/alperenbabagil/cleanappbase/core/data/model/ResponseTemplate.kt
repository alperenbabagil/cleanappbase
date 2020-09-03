package com.alperenbabagil.cleanappbase.core.data.model


import com.alperenbabagil.cabdomain.CoreDomainConstants
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.main.data.MainDataConstants
import com.google.gson.annotations.SerializedName

data class ResponseTemplate<T>(
    @SerializedName("data")
    var `data`: T?=null,
    @SerializedName("error")
    var error: Error?=null,
    @SerializedName("status")
    var status: String?=CoreDataConstants.SERVER_STATUS_SUCCESS
)