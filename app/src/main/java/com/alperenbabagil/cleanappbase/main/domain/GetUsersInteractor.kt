package com.alperenbabagil.cleanappbase.main.domain

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleRetrieveInteractor
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import com.alperenbabagil.cleanappbase.main.domain.model.UserListRequestType

class GetUsersInteractor(private val userRepository: UserRepository) :
    BaseSingleInteractor<GetUsersInteractor.Params,List<UserListItem>>() {

    class Params(val userListRequestType: UserListRequestType) : Interactor.Params()

    override suspend fun executeInteractor(params: Params): DataHolder<List<UserListItem>> =
        userRepository.getUsers(params.userListRequestType)
}