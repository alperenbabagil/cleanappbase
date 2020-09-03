package com.alperenbabagil.cleanappbase.detail.data

import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.core.data.model.CABDemoBaseApiResponse
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailDataTemplate
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailNetworkDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ProfileService {
    @POST("q")
    suspend fun getProfileDetail(@Body baseRequest: BaseRequest<ResponseTemplate<ProfileDetailDataTemplate>>) :
            Response<CABDemoBaseApiResponse<ProfileDetailNetworkDTO>>
}