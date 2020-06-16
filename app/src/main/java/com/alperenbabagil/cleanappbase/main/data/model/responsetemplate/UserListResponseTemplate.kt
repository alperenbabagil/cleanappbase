package com.alperenbabagil.cleanappbase.main.data.model.responsetemplate


import com.alperenbabagil.cabdomain.CoreDomainConstants
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.main.data.MainDataConstants
import com.google.gson.annotations.SerializedName

data class UserListResponseTemplate(
    @SerializedName("data")
    var `data`: Data?=Data(),
    @SerializedName("error")
    var error: Error?=null,
    @SerializedName("status")
    var status: String?=CoreDataConstants.SERVER_STATUS_SUCCESS
)