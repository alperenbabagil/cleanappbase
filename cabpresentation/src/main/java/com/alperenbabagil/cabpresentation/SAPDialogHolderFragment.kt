package com.alperenbabagil.cabpresentation

import androidx.fragment.app.Fragment
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.simpleanimationpopuplibrary.SapFragment
import com.alperenbabagil.simpleanimationpopuplibrary.removeCurrentDialog
import com.alperenbabagil.simpleanimationpopuplibrary.showLoadingDialog as fragmentLoading

interface DialogHolderFragment : SapFragment {

    fun loadingDialogDismissed()

    val cabViewModel : CABViewModel?
}

fun DialogHolderFragment.showDHLoadingDialog(forceCancellable:Boolean=false,
                                             dataHolder: DataHolder.Loading?=null,
                                             cancelledCallback: () -> Unit ={}
){
    (this as? Fragment)?.let {
        var isCancellable=dataHolder?.cancellable ?:false
        if(forceCancellable) isCancellable=true
        fragmentLoading(isCancellable = isCancellable){
            removeCurrentDialog()
            dataHolder?.let { dataHolderLoading->
                cabViewModel?.loadingCancelled(dataHolderLoading.tag)
            }
            cancelledCallback.invoke()
        }
    }
}