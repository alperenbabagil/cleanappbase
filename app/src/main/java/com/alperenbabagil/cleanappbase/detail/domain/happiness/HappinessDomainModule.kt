package com.alperenbabagil.cleanappbase.detail.domain.happiness

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.BaseSingleFlowInteractor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val happinessDomainModule = module{
    factory(named<HappinessInteractor>()) {
        HappinessInteractor(get())
    } bind BaseSingleFlowInteractor::class
}