package com.alperenbabagil.cleanappbase.main.domain

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import org.koin.core.qualifier.named
import org.koin.dsl.module

val mainDomainModule = module {
    factory(named<GetUsersInteractor>()) {
        GetUsersInteractor(get()) as
                Interactor.SingleInteractor<GetUsersInteractor.Params,List<UserListItem>>
    }
}