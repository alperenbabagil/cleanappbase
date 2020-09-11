package com.alperenbabagil.cleanappbase.core.presentation

import org.koin.dsl.module

val presentationCoreModule = module {
    single {
        AppNavigator()
    }
}