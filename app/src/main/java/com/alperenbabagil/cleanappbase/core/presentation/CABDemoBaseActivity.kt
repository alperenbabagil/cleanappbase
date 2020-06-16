package com.alperenbabagil.cleanappbase.core.presentation

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.alperenbabagil.cabpresentation.CABActivity
import com.alperenbabagil.cabpresentation.CABViewModel
import com.alperenbabagil.cabpresentation.navigation.CABNavigator
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

abstract class CABDemoBaseActivity : AppCompatActivity(),CABActivity {

    override val cabNavigator: CABNavigator by inject()

    override var currentDialog: Dialog?=null

    override var isFullScreenNow: Boolean = false

    override fun loadingDialogDismissed() {
        //to override
    }

}