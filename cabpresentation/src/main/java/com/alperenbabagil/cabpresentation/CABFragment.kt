package com.alperenbabagil.cabpresentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.dataholder.FailType

interface CABFragment : DialogHost {
    override fun <T : Any> getInterceptorLambda(): ((dataHolder: DataHolder<T>) -> Boolean)? {
        return null
    }
}

fun <T : Any>CABFragment.handleDataHolderResult(showDialogsInFragment:Boolean=true,
                                                dataHolder: DataHolder<T>,
                                                errorBody : ((DataHolder.Fail) -> Unit)?=null,
                                                errorButtonClick : ((DataHolder.Fail) -> Unit)?=null,
                                                bypassErrorHandling:Boolean=false,
                                                bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                                observeSuccessValueOnce:Boolean=false,
                                                observeFailValueOnce:Boolean=false,
                                                successBody : ((data:T) -> Unit)?=null

){
    when(dataHolder){
        is DataHolder.Success ->{
            if(observeSuccessValueOnce && dataHolder.isObserved) return
            if(showDialogsInFragment){
                if(!bypassDisableCurrentPopupOnSuccess) dismissCurrentDialog()
            }
            else{
                ((this as? Fragment)?.requireActivity() as? CABActivity)?.let{
                    if(!bypassDisableCurrentPopupOnSuccess) it.dismissCurrentDialog()
                }
            }
            successBody?.invoke(dataHolder.data)
            dataHolder.setObserved()
        }
        is DataHolder.Fail ->{
            if(observeFailValueOnce && dataHolder.isObserved) return
            if(bypassErrorHandling){
                errorBody?.invoke(dataHolder)
            }
            else{
                if(showDialogsInFragment){
                    when(dataHolder.failType){
                        FailType.ERROR->{
                            showErrorDialog(
                                titleRes = R.string.error,
                                errorRes = dataHolder.errorResourceId?:-1,
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
                else{
                    when(dataHolder.failType){
                        FailType.ERROR->{
                            ((this as? Fragment)?.requireActivity() as? CABActivity)?.showErrorDialog(
                                titleRes = R.string.error,
                                errorRes = dataHolder.errorResourceId?:-1,
                                errorStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = {
                                    errorButtonClick?.invoke(dataHolder)
                                },
                                isCancellable = dataHolder.cancellable
                            )
                        }
                        FailType.INFO ->{
                            ((this as? Fragment)?.requireActivity() as? CABActivity)?.showInfoDialog(titleRes = R.string.info,
                                infoRes = dataHolder.errorResourceId?:-1,
                                infoStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = {
                                    errorButtonClick?.invoke(dataHolder)
                                },
                                isCancellable = dataHolder.cancellable)
                        }
                        FailType.WARNING->{
                            ((this as? Fragment)?.requireActivity() as? CABActivity)?.showWarningDialog(titleRes = R.string.warning,
                                warningRes = dataHolder.errorResourceId?:-1,
                                warningStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = {
                                    errorButtonClick?.invoke(dataHolder)
                                },
                                isCancellable = dataHolder.cancellable)
                        }
                    }
                }
            }
            dataHolder.setObserved()
        }
        is DataHolder.Loading ->{
            if(showDialogsInFragment){
                showLoadingDialog(dataHolder = dataHolder)
            }
            else{
                ((this as? Fragment)?.requireActivity() as? CABActivity)?.
                    showLoadingDialog(dataHolder = dataHolder)
            }
        }
    }
}

fun <T : Any>CABFragment.observeDataHolder(showDialogsInFragment:Boolean=true,
                                           liveData: LiveData<DataHolder<T>>,
                                           errorBody : ((DataHolder.Fail) -> Unit)?=null,
                                           errorButtonClick : ((DataHolder.Fail) -> Unit)?=null,
                                           bypassErrorHandling:Boolean=false,
                                           bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                           observeSuccessValueOnce:Boolean=false,
                                           observeFailValueOnce:Boolean=false,
                                           successBody : ((data:T) -> Unit)?=null){
    liveData.observe(this as LifecycleOwner, { dataHolder ->
        handleDataHolderResult(showDialogsInFragment,
            dataHolder,
            errorBody,
            errorButtonClick,
            bypassErrorHandling,
            bypassDisableCurrentPopupOnSuccess,
            observeSuccessValueOnce,
            observeFailValueOnce,
            successBody)
    })
}