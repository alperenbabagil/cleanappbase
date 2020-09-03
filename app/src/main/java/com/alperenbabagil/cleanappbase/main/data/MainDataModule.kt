package com.alperenbabagil.cleanappbase.main.data

import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.main.domain.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit

val mainDataModule = module {

    factory {
        get<Retrofit>().create(UserService::class.java)
    }

    factory(named<GetUsersDataSource>()) {
        GetUsersDataSource(get(),get())
    } bind BaseDataSource::class

    factory {
        UserRepositoryImpl(get(named<GetUsersDataSource>()))
    } bind UserRepository::class
}