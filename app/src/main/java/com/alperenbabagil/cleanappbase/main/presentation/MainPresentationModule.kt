package com.alperenbabagil.cleanappbase.main.presentation

import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseViewModel
import com.alperenbabagil.cleanappbase.main.domain.GetUsersInteractor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val mainPresentationModule = module {
    viewModel(named<MainViewModel>()) {
        MainViewModel(get(named<GetUsersInteractor>()))
    } bind CABDemoBaseViewModel::class
}