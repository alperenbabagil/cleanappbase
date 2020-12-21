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
                                                   errorBody : ((dataHolder: DataHolder.Fail) -> Unit)?=null,
                                                   errorButtonClick : ((dataHolder: DataHolder.Fail) -> Unit)?=null,
                                                   bypassErrorHandling:Boolean=false,
                                                   bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                                   observeSuccessValueOnce:Boolean=false,
                                                   interceptor: ((dataHolder: DataHolder<T>) -> Boolean)?=null,
                                                   observeFailValueOnce:Boolean=false,
                                                   successBody: ((data: T) -> Unit)? = null

){
    if(interceptor?.invoke(dataHolder)==true) return
    when(dataHolder){
        is DataHolder.Success ->{
            if(observeSuccessValueOnce && dataHolder.isObserved) return

            if(!bypassDisableCurrentPopupOnSuccess) currentDialog?.dismiss()
            successBody?.invoke(dataHolder.data)
            dataHolder.setObserved()
        }
        is DataHolder.Fail ->{
            if(observeFailValueOnce && dataHolder.isObserved) return

            currentDialog?.dismiss()
            if(bypassErrorHandling){
                errorBody?.invoke(dataHolder)
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
                                    if(!it.invoke(dataHolder)) errorButtonClick?.invoke(dataHolder)
                                } ?: run{
                                    errorButtonClick?.invoke(dataHolder)
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
                                    if(!it.invoke(dataHolder)) errorButtonClick?.invoke(dataHolder)
                                } ?: run{
                                    errorButtonClick?.invoke(dataHolder)
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
                                    if(!it.invoke(dataHolder)) errorButtonClick?.invoke(dataHolder)
                                } ?: run{
                                    errorButtonClick?.invoke(dataHolder)
                                }
                            },
                            isCancellable = dataHolder.cancellable)
                    }
                }
            }
            dataHolder.setObserved()
        }
        is DataHolder.Loading -> {
            showDHLoadingDialog(dataHolder = dataHolder)
        }
    }
}

fun <T : Any>CABSAPActivity.observeDataHolder(liveData: LiveData<DataHolder<T>>,
                                              errorBody : ((dataHolder: DataHolder.Fail) -> Unit)?=null,
                                              errorButtonClick : ((dataHolder: DataHolder.Fail) -> Unit)?=null,
                                              bypassErrorHandling:Boolean=false,
                                              bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                              observeSuccessValueOnce:Boolean=false,
                                              interceptor: ((dataHolder: DataHolder<T>) -> Boolean)?=null,
                                              observeFailValueOnce:Boolean=false,
                                              successBody : ((data:T) -> Unit)?=null){
    liveData.observe(this as LifecycleOwner, { dataHolder ->
        handleDataHolderResult(dataHolder,
            errorBody,
            errorButtonClick,
            bypassErrorHandling,
            bypassDisableCurrentPopupOnSuccess,
            observeSuccessValueOnce,
            interceptor,
            observeFailValueOnce,
            successBody)
    })
}