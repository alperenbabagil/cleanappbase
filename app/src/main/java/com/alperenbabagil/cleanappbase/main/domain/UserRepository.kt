package com.alperenbabagil.cleanappbase.main.domain

import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import com.alperenbabagil.cleanappbase.main.domain.model.UserListRequestType

interface UserRepository {
    suspend fun getUsers(userListRequestType: UserListRequestType) : DataHolder<List<UserListItem>>
}