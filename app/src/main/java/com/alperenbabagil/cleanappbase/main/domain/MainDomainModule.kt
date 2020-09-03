package com.alperenbabagil.cleanappbase.main.domain

import com.alperenbabagil.cabdomain.Interactor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val mainDomainModule = module {
    factory(named<GetUsersInteractor>()) {
        GetUsersInteractor(get())
    } bind Interactor.SingleInteractor::class
}