package com.alperenbabagil.cleanappbase.detail.domain.profiledetail

import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val profileDetailDomainModule = module{
    factory(named<GetProfileDetailInteractor>()) { 
        GetProfileDetailInteractor(get())
    } bind BaseSingleInteractor::class
}