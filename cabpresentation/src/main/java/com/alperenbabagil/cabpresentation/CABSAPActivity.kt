package com.alperenbabagil.cabpresentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alperenbabagil.simpleanimationpopuplibrary.showErrorDialog

interface CABSAPActivity : DialogHolderActivity{
}

fun <T : Any>CABSAPActivity.handleDataHolderResult(dataHolder: com.alperenbabagil.dataholder.DataHolder<T>,
                                                   errorBody : (errorStr:String,errorResId:Int) -> Unit,
                                                   errorButtonClick : () -> Unit,
                                                   bypassErrorHandling:Boolean,
                                                   bypassDisableCurrentPopupOnSuccess:Boolean,
                                                   successBody : (data:T) -> Unit

){
    if(dataHolder is com.alperenbabagil.dataholder.DataHolder.Success){
        if(!bypassDisableCurrentPopupOnSuccess) currentDialog?.dismiss()
        successBody.invoke(dataHolder.data)
    }

    if(dataHolder is com.alperenbabagil.dataholder.DataHolder.Fail){
        currentDialog?.dismiss()
        if(bypassErrorHandling){
            errorBody.invoke(dataHolder.errStr,dataHolder.errorResourceId?: -1)
        }
        else{
            showErrorDialog(errorRes = dataHolder.errorResourceId,
                errorStr = dataHolder.errStr,
                isCancellable = false,
                positiveButtonStrRes = R.string.ok,
                positiveButtonClick = errorButtonClick)
        }
    }
    if(dataHolder is com.alperenbabagil.dataholder.DataHolder.Loading){
        showDHLoadingDialog(dataHolder = dataHolder)
    }
}

fun <T : Any>CABSAPActivity.observeDataHolder(liveData: LiveData<com.alperenbabagil.dataholder.DataHolder<T>>,
                                              errorBody : (errorStr:String,errorResId:Int) -> Unit = {_,_ -> },
                                              errorButtonClick : () -> Unit = {},
                                              bypassErrorHandling:Boolean=false,
                                              bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                              successBody : (data:T) -> Unit){
    liveData.observe(this as LifecycleOwner, Observer { dataHolder ->
        handleDataHolderResult(dataHolder,
            errorBody,
            errorButtonClick,
            bypassErrorHandling,
            bypassDisableCurrentPopupOnSuccess,
            successBody)
    })
}