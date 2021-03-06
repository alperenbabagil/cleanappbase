package com.alperenbabagil.cleanappbase.main.data

import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.core.data.model.Error
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.main.data.model.UserListDataTemplate
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO
import com.alperenbabagil.cleanappbase.main.data.model.mapToUserListItem
import com.alperenbabagil.cleanappbase.main.domain.UserRepository
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.dataholder.handleSuccess


class UserRepositoryImpl(private val getUsersDataSource:
                         BaseDataSource<ResponseTemplate<UserListDataTemplate>,
                                 List<UserListItemNetworkDTO>>
): UserRepository {

    override suspend fun getUsers(requestResultType: RequestResultType)
            : DataHolder<List<UserListItem>> {
        val requestTemplate=when(requestResultType){
            RequestResultType.SUCCESS->{
                ResponseTemplate(data=UserListDataTemplate())
            }
            RequestResultType.FAIL->{
                ResponseTemplate<UserListDataTemplate>().apply {
                    status=CoreDataConstants.SERVER_STATUS_FAIL
                    data=null
                    error= Error(
                        7,
                        "Server business error"
                    )
                }
            }
        }
        return getUsersDataSource.getResult(requestTemplate).handleSuccess {
            it.data.map { it.mapToUserListItem() }
        }
    }


}