package com.alperenbabagil.cleanappbase.detail.data.profiledetail

import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.ProfileDetailRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val profileDetailDataModule = module {

    factory {
        ProfileDetailRepositoryImpl(get(named<ProfileDetailDataSource>()))
    } bind ProfileDetailRepository::class

    factory(named<ProfileDetailDataSource>()) {
        ProfileDetailDataSource(get(),get())
    } bind BaseDataSource::class
}