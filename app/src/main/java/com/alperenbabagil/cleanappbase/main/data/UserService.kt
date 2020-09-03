package com.alperenbabagil.cleanappbase.main.data

import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.core.data.model.CABDemoBaseApiResponse
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.main.data.model.UserListDataTemplate
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("q")
    suspend fun getUsers(@Body baseRequest: BaseRequest<ResponseTemplate<UserListDataTemplate>>) :
            Response<CABDemoBaseApiResponse<List<UserListItemNetworkDTO>>>
}