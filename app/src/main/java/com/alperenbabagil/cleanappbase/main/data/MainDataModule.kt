package com.alperenbabagil.cleanappbase.main.data

import com.alperenbabagil.cabdata.DataSource
import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO
import com.alperenbabagil.cleanappbase.main.data.model.responsetemplate.UserListResponseTemplate
import com.alperenbabagil.cleanappbase.main.domain.UserRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val mainDataModule = module {

    factory {
        get<Retrofit>().create(UserService::class.java)
    }

    factory(named<GetUsersDataSource>()) {
        GetUsersDataSource(get(),get()) as
                DataSource.AsyncDataSource.RequestDataSource<UserListResponseTemplate,
                        List<UserListItemNetworkDTO>>
    }

    factory {
        UserRepositoryImpl(get(named<GetUsersDataSource>())) as UserRepository
    }
}