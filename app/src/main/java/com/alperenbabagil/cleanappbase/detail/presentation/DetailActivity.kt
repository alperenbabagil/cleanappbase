package com.alperenbabagil.cleanappbase.detail.presentation

import android.os.Bundle
import android.os.PersistableBundle
import com.alperenbabagil.cabpresentation.CABViewModel
import com.alperenbabagil.cleanappbase.R
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseActivity
import com.alperenbabagil.cleanappbase.main.presentation.MainViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named

class DetailActivity() : CABDemoBaseActivity() {

    override val cabViewModel: DetailViewModel by viewModel(named<DetailViewModel>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)
    }
}