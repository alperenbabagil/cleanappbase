package com.alperenbabagil.cleanappbase.main.domain

import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem

interface UserRepository {
    suspend fun getUsers(requestResultType: RequestResultType) : DataHolder<List<UserListItem>>
}