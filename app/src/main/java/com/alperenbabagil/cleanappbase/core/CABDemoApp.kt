package com.alperenbabagil.cleanappbase.core

import android.app.Application
import com.alperenbabagil.cleanappbase.core.data.dataCoreModule
import com.alperenbabagil.cleanappbase.core.domain.domainCoreModule
import com.alperenbabagil.cleanappbase.core.presentation.presentationCoreModule
import com.alperenbabagil.cleanappbase.detail.data.detailDataModule
import com.alperenbabagil.cleanappbase.detail.domain.detailDomainModule
import com.alperenbabagil.cleanappbase.detail.presentation.detailPresentationModule
import com.alperenbabagil.cleanappbase.main.data.mainDataModule
import com.alperenbabagil.cleanappbase.main.domain.mainDomainModule
import com.alperenbabagil.cleanappbase.main.presentation.mainPresentationModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CABDemoApp : Application() {

    @ExperimentalCoroutinesApi
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@CABDemoApp)
            modules(presentationCoreModule +
                    domainCoreModule +
                    dataCoreModule +
                    detailDataModule +
                    detailDomainModule +
                    detailPresentationModule +
                    mainDataModule +
                    mainDomainModule +
                    mainPresentationModule
            )
        }
    }
}