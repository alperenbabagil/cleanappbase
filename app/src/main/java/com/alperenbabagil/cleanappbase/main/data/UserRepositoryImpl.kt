package com.alperenbabagil.cleanappbase.main.data

import com.alperenbabagil.cabdata.DataSource
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO
import com.alperenbabagil.cleanappbase.main.data.model.mapToUserListItem
import com.alperenbabagil.cleanappbase.main.data.model.responsetemplate.Error
import com.alperenbabagil.cleanappbase.main.data.model.responsetemplate.UserListResponseTemplate
import com.alperenbabagil.cleanappbase.main.domain.UserRepository
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import com.alperenbabagil.cleanappbase.main.domain.model.UserListRequestType
import java.net.Authenticator


class UserRepositoryImpl(private val getUsersDataSource:
                         DataSource.AsyncDataSource.RequestDataSource<UserListResponseTemplate
                                 ,List<UserListItemNetworkDTO>>): UserRepository {

    override suspend fun getUsers(userListRequestType: UserListRequestType)
            : DataHolder<List<UserListItem>> {
        val requestTemplate=when(userListRequestType){
            UserListRequestType.SUCCESS->{
                UserListResponseTemplate()
            }
            UserListRequestType.FAIL->{
                UserListResponseTemplate().apply {
                    status=CoreDataConstants.SERVER_STATUS_FAIL
                    data=null
                    error=Error(31,
                        "Server business error")
                }
            }
        }
        return when(val res=getUsersDataSource.getResult(requestTemplate)){
            is DataHolder.Success -> DataHolder.Success(res.data.map { it.mapToUserListItem() })
            is DataHolder.Fail -> res
            is DataHolder.Loading -> res
        }

    }


}