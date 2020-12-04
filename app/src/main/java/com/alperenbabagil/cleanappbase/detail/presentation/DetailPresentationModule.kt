package com.alperenbabagil.cleanappbase.detail.presentation

import com.alperenbabagil.cleanappbase.detail.presentation.changepp.changePPPresentationModule
import com.alperenbabagil.cleanappbase.detail.presentation.happiness.happinessPresentationModule
import com.alperenbabagil.cleanappbase.detail.presentation.profiledetail.profileDetailPresentationModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val detailBasePresentationModule=module{
    viewModel(named<DetailViewModel>()) {
        DetailViewModel()
    }
}

val detailPresentationModule = detailBasePresentationModule +
        profileDetailPresentationModule +
        changePPPresentationModule +
        happinessPresentationModule