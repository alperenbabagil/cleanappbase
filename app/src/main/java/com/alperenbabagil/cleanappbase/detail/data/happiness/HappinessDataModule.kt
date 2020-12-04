package com.alperenbabagil.cleanappbase.detail.data.happiness

import com.alperenbabagil.cabdata.DataSource
import com.alperenbabagil.cleanappbase.core.data.BaseFlowDataSource
import com.alperenbabagil.cleanappbase.detail.domain.happiness.HappinessRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val happinessDataModule = module{

    factory {
        HappinessGenerator(get())
    }

    factory {
        HappinessProducer()
    }

    factory(named<HappinessDataSource>()) {
        HappinessDataSource(get())
    } bind BaseFlowDataSource::class

    factory {
        HappinessRepositoryImpl(get(named<HappinessDataSource>()))
    } bind HappinessRepository::class
}