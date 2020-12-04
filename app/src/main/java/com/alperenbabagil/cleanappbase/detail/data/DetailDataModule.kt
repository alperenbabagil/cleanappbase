package com.alperenbabagil.cleanappbase.detail.data

import com.alperenbabagil.cleanappbase.detail.data.happiness.happinessDataModule
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.profileDetailDataModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module
import retrofit2.Retrofit

val detailBaseDataModule = module {
    factory {
        get<Retrofit>().create(ProfileService::class.java)
    }
}

@ExperimentalCoroutinesApi
val detailDataModule = detailBaseDataModule + profileDetailDataModule + happinessDataModule