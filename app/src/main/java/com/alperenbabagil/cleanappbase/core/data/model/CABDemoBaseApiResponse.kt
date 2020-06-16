package com.alperenbabagil.cleanappbase.core.data.model

import com.alperenbabagil.cabdata.model.BaseApiResponse
import com.google.gson.annotations.SerializedName

data class CABDemoBaseApiResponse<T>(@SerializedName("status") val status:String,
                                     @SerializedName("data") val data:T,
                                     @SerializedName("error") val serverError: ServerError?
                                     ) : BaseApiResponse<T>