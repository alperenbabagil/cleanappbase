package com.alperenbabagil.cleanappbase.main.data

import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.main.data.model.UserListDataTemplate
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO

class GetUsersDataSource(private val userService: UserService,
                         private val apiCallAdapter: ApiCallAdapter):
    BaseDataSource<ResponseTemplate<UserListDataTemplate>,List<UserListItemNetworkDTO>>() {

    override suspend fun getDataSourceResult(request: BaseRequest<ResponseTemplate<UserListDataTemplate>>):
            DataHolder<List<UserListItemNetworkDTO>> =
        apiCallAdapter.adapt {
            userService.getUsers(request)
        }
}