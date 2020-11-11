package com.alperenbabagil.cleanappbase.core.presentation

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import com.alperenbabagil.cabpresentation.CABSAPActivity
import org.koin.android.ext.android.get

abstract class CABDemoBaseActivity : AppCompatActivity(), CABSAPActivity,NavigatorOwner {

    override fun getNavigator(): AppNavigator = get()

    override var currentDialog: Dialog?=null

    override var isFullScreenNow: Boolean = false

    override fun loadingDialogDismissed() {
        //to override
    }
}