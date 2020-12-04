package com.alperenbabagil.cleanappbase.detail.presentation.happiness

import com.alperenbabagil.cleanappbase.detail.domain.happiness.HappinessInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val happinessPresentationModule = module {
    viewModel(named<HappinessViewModel>()) {
        HappinessViewModel(get(named<HappinessInteractor>()))
    }
}