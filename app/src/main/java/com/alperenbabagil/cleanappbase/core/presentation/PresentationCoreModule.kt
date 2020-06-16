package com.alperenbabagil.cleanappbase.core.presentation

import com.alperenbabagil.cabpresentation.navigation.CABNavigator
import org.koin.dsl.module

val presentationCoreModule = module {

    single {
        AppNavigator() as CABNavigator
    }
}