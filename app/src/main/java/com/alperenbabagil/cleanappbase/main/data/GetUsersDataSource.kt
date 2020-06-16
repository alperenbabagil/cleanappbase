package com.alperenbabagil.cleanappbase.main.data

import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.cabdata.model.BaseApiResponse
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO
import com.alperenbabagil.cleanappbase.main.data.model.responsetemplate.UserListResponseTemplate
import retrofit2.Response

class GetUsersDataSource(private val userService: UserService,
                         private val apiCallAdapter: ApiCallAdapter):
    BaseDataSource<UserListResponseTemplate,List<UserListItemNetworkDTO>>() {

    override suspend fun getDataSourceResult(request: BaseRequest<UserListResponseTemplate>):
            DataHolder<List<UserListItemNetworkDTO>> =
        apiCallAdapter.adapt {
            userService.getUsers(request)
        }
}