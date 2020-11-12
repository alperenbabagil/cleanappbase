package com.alperenbabagil.cabpresentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.dataholder.FailType
import com.alperenbabagil.simpleanimationpopuplibrary.showErrorDialog
import com.alperenbabagil.simpleanimationpopuplibrary.showInfoDialog
import com.alperenbabagil.simpleanimationpopuplibrary.showWarningDialog

interface CABSAPActivity : DialogHolderActivity{
    override fun <T : Any> getInterceptorLambda(): ((dataHolder: DataHolder<T>) -> Boolean)? {
        return null
    }
}

fun <T : Any>CABSAPActivity.handleDataHolderResult(dataHolder: DataHolder<T>,
                                                   errorBody : (errorStr:String,errorResId:Int) -> Unit,
                                                   errorButtonClick : () -> Unit,
                                                   bypassErrorHandling:Boolean,
                                                   bypassDisableCurrentPopupOnSuccess:Boolean,
                                                   observeSuccessValueOnce:Boolean=false,
                                                   observeFailValueOnce:Boolean=false,
                                                   successBody : (data:T) -> Unit

){
    if(dataHolder is DataHolder.Success){
        if(observeSuccessValueOnce && dataHolder.isObserved) return

        if(!bypassDisableCurrentPopupOnSuccess) currentDialog?.dismiss()
        successBody.invoke(dataHolder.data)
        dataHolder.setObserved()
    }

    if(dataHolder is DataHolder.Fail){
        if(observeFailValueOnce && dataHolder.isObserved) return

        currentDialog?.dismiss()
        if(bypassErrorHandling){
            errorBody.invoke(dataHolder.errStr,dataHolder.errorResourceId?: -1)
        }
        else{
            when(dataHolder.failType){
                FailType.ERROR -> {
                    showErrorDialog(
                        titleRes = R.string.error,
                        errorRes = dataHolder.errorResourceId ?: -1,
                        errorStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = {
                            getInterceptorLambda<T>()?.let {
                                if(!it.invoke(dataHolder)) errorButtonClick.invoke()
                            } ?: run{
                                errorButtonClick.invoke()
                            }
                        },
                        isCancellable = dataHolder.cancellable
                    )
                }
                FailType.INFO ->{
                    showInfoDialog(titleRes = R.string.info,
                        infoRes = dataHolder.errorResourceId?:-1,
                        infoStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = {
                            getInterceptorLambda<T>()?.let {
                                if(!it.invoke(dataHolder)) errorButtonClick.invoke()
                            } ?: run{
                                errorButtonClick.invoke()
                            }
                        },
                        isCancellable = dataHolder.cancellable)
                }
                FailType.WARNING->{
                    showWarningDialog(titleRes = R.string.warning,
                        warningRes = dataHolder.errorResourceId?:-1,
                        warningStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = {
                            getInterceptorLambda<T>()?.let {
                                if(!it.invoke(dataHolder)) errorButtonClick.invoke()
                            } ?: run{
                                errorButtonClick.invoke()
                            }
                        },
                        isCancellable = dataHolder.cancellable)
                }
            }
        }
        dataHolder.setObserved()
    }

    if(dataHolder is DataHolder.Loading){
        showDHLoadingDialog(dataHolder = dataHolder)
    }
}

fun <T : Any>CABSAPActivity.observeDataHolder(liveData: LiveData<DataHolder<T>>,
                                              errorBody : (errorStr:String,errorResId:Int) -> Unit = {_,_ -> },
                                              errorButtonClick : () -> Unit = {},
                                              bypassErrorHandling:Boolean=false,
                                              bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                              observeSuccessValueOnce:Boolean=false,
                                              observeFailValueOnce:Boolean=false,
                                              successBody : (data:T) -> Unit){
    liveData.observe(this as LifecycleOwner, { dataHolder ->
        handleDataHolderResult(dataHolder,
            errorBody,
            errorButtonClick,
            bypassErrorHandling,
            bypassDisableCurrentPopupOnSuccess,
            observeSuccessValueOnce,
            observeFailValueOnce,
            successBody)
    })
}