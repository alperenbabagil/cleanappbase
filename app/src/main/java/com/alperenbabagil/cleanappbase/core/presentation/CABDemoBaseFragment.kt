package com.alperenbabagil.cleanappbase.core.presentation

import android.view.View
import androidx.fragment.app.Fragment
import com.alperenbabagil.cabpresentation.CABSAPFragment

abstract class CABDemoBaseFragment() : Fragment(),CABSAPFragment {

    override var currentDialogView: View?=null

    override fun loadingDialogDismissed() {
        // to override
    }
}