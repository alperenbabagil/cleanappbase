package com.alperenbabagil.cleanappbase.detail.presentation.profiledetail

import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.GetProfileDetailInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val profileDetailPresentationModule = module {

    viewModel(named<ProfileDetailViewModel>()) {
        ProfileDetailViewModel(get(named<GetProfileDetailInteractor>()))
    }
}