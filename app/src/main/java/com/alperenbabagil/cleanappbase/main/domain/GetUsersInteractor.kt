package com.alperenbabagil.cleanappbase.main.domain

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem

class GetUsersInteractor(private val userRepository: UserRepository) :
    BaseSingleInteractor<GetUsersInteractor.Params,List<UserListItem>>() {

    data class Params(val requestResultType: RequestResultType) : Interactor.Params()

    override suspend fun executeInteractor(params: Params): com.alperenbabagil.dataholder.DataHolder<List<UserListItem>> =
        userRepository.getUsers(params.requestResultType)
}