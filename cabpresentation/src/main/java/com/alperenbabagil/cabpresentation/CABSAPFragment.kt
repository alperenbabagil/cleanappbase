package com.alperenbabagil.cabpresentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.dataholder.FailType
import com.alperenbabagil.simpleanimationpopuplibrary.showErrorDialog
import com.alperenbabagil.simpleanimationpopuplibrary.showInfoDialog
import com.alperenbabagil.simpleanimationpopuplibrary.showWarningDialog

interface CABSAPFragment : DialogHolderFragment {
    override fun <T : Any> getInterceptorLambda(): ((dataHolder: DataHolder<T>) -> Boolean)? {
        return null
    }
}

fun <T : Any>CABSAPFragment.handleDataHolderResult(interceptor: ((dataHolder: DataHolder<T>) -> Boolean)?=null,
                                                   showDialogsInFragment:Boolean=true,
                                                   dataHolder: DataHolder<T>,
                                                   errorBody : ((errorStr:String,errorResId:Int) -> Unit)?=null,
                                                   errorButtonClick : (() -> Unit)?=null,
                                                   bypassErrorHandling:Boolean=false,
                                                   bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                                   observeSuccessValueOnce:Boolean=false,
                                                   observeFailValueOnce:Boolean=false,
                                                   successBody : ((data:T) -> Unit)?=null

){
    if(interceptor?.invoke(dataHolder)==true) return
    when(dataHolder){
        is DataHolder.Success ->{
            if(observeSuccessValueOnce && dataHolder.isObserved) return
            if(showDialogsInFragment){
                if(!bypassDisableCurrentPopupOnSuccess) currentDialogView?.hide()
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
                errorBody?.invoke(dataHolder.errStr,dataHolder.errorResourceId?: -1)
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
                                        if(!it.invoke(dataHolder)) errorButtonClick?.invoke()
                                    } ?: run{
                                        errorButtonClick?.invoke()
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
                                        if(!it.invoke(dataHolder)) errorButtonClick?.invoke()
                                    } ?: run{
                                        errorButtonClick?.invoke()
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
                                        if(!it.invoke(dataHolder)) errorButtonClick?.invoke()
                                    } ?: run{
                                        errorButtonClick?.invoke()
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
                                    getInterceptorLambda<T>()?.let {
                                        if(!it.invoke(dataHolder)) errorButtonClick?.invoke()
                                    } ?: run{
                                        errorButtonClick?.invoke()
                                    }
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
                                    getInterceptorLambda<T>()?.let {
                                        if(!it.invoke(dataHolder)) errorButtonClick?.invoke()
                                    } ?: run{
                                        errorButtonClick?.invoke()
                                    }
                                },
                                isCancellable = dataHolder.cancellable)
                        }
                        FailType.WARNING->{
                            ((this as? Fragment)?.requireActivity() as? CABActivity)?.showWarningDialog(titleRes = R.string.warning,
                                warningRes = dataHolder.errorResourceId?:-1,
                                warningStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = {
                                    getInterceptorLambda<T>()?.let {
                                        if(!it.invoke(dataHolder)) errorButtonClick?.invoke()
                                    } ?: run{
                                        errorButtonClick?.invoke()
                                    }
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
                showDHLoadingDialog(dataHolder = dataHolder)
            }
            else{
                ((this as? Fragment)?.requireActivity() as? CABSAPActivity)?.
                showDHLoadingDialog(dataHolder = dataHolder)
            }
        }
    }
}

fun <T : Any>CABSAPFragment.observeDataHolder(interceptor: ((dataHolder: DataHolder<T>) -> Boolean)?=null,
                                              showDialogsInFragment:Boolean=true,
                                              liveData: LiveData<DataHolder<T>>,
                                              errorBody : ((errorStr:String,errorResId:Int) -> Unit)?=null,
                                              errorButtonClick : (() -> Unit)?=null,
                                              bypassErrorHandling:Boolean=false,
                                              bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                              observeSuccessValueOnce:Boolean=false,
                                              observeFailValueOnce:Boolean=false,
                                              successBody : ((data:T) -> Unit)?=null){
    liveData.observe(this as LifecycleOwner, { dataHolder ->
        handleDataHolderResult(
            interceptor,
            showDialogsInFragment,
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