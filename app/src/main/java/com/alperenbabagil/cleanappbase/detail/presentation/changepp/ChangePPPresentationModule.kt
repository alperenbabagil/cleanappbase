package com.alperenbabagil.cleanappbase.detail.presentation.changepp

import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.GetProfileDetailInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val changePPPresentationModule = module {
    viewModel(named<ChangePPViewModel>()) {
        ChangePPViewModel(get(named<GetProfileDetailInteractor>()))
    }
}