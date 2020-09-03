package com.alperenbabagil.cleanappbase.detail.data

import com.alperenbabagil.cleanappbase.detail.data.profiledetail.profileDetailDataModule
import org.koin.dsl.module
import retrofit2.Retrofit

val detailBaseDataModule = module {
    factory {
        get<Retrofit>().create(ProfileService::class.java)
    }
}

val detailDataModule = detailBaseDataModule + profileDetailDataModule