package com.alperenbabagil.cabpresentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.dataholder.FailType

interface CABFragment : DialogHost {

}

fun <T : Any>CABFragment.handleDataHolderResult(showDialogsInFragment:Boolean=true,
                                                dataHolder: DataHolder<T>,
                                                errorBody : (errorStr:String,errorResId:Int) -> Unit,
                                                errorButtonClick : () -> Unit,
                                                bypassErrorHandling:Boolean,
                                                bypassDisableCurrentPopupOnSuccess:Boolean,
                                                successBody : (data:T) -> Unit

){
    when(dataHolder){
        is DataHolder.Success ->{
            if(showDialogsInFragment){
                if(!bypassDisableCurrentPopupOnSuccess) dismissCurrentDialog()
            }
            else{
                ((this as? Fragment)?.requireActivity() as? CABActivity)?.let{
                    if(!bypassDisableCurrentPopupOnSuccess) it.dismissCurrentDialog()
                }
            }
            successBody.invoke(dataHolder.data)
        }
        is DataHolder.Fail ->{
            if(bypassErrorHandling){
                errorBody.invoke(dataHolder.errStr,dataHolder.errorResourceId?: -1)
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
                                positiveButtonClick = errorButtonClick,
                                isCancellable = dataHolder.cancellable
                            )
                        }
                        FailType.INFO ->{
                            showInfoDialog(titleRes = R.string.info,
                                infoRes = dataHolder.errorResourceId?:-1,
                                infoStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = errorButtonClick,
                                isCancellable = dataHolder.cancellable)
                        }
                        FailType.WARNING->{
                            showWarningDialog(titleRes = R.string.warning,
                                warningRes = dataHolder.errorResourceId?:-1,
                                warningStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = errorButtonClick,
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
                                positiveButtonClick = errorButtonClick,
                                isCancellable = dataHolder.cancellable
                            )
                        }
                        FailType.INFO ->{
                            ((this as? Fragment)?.requireActivity() as? CABActivity)?.showInfoDialog(titleRes = R.string.info,
                                infoRes = dataHolder.errorResourceId?:-1,
                                infoStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = errorButtonClick,
                                isCancellable = dataHolder.cancellable)
                        }
                        FailType.WARNING->{
                            ((this as? Fragment)?.requireActivity() as? CABActivity)?.showWarningDialog(titleRes = R.string.warning,
                                warningRes = dataHolder.errorResourceId?:-1,
                                warningStr = dataHolder.errStr,
                                positiveButtonStrRes = R.string.ok,
                                positiveButtonClick = errorButtonClick,
                                isCancellable = dataHolder.cancellable)
                        }
                    }
                }
            }
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
                                           errorBody : (errorStr:String,errorResId:Int) -> Unit = {_,_ -> },
                                           errorButtonClick : () -> Unit = {},
                                           bypassErrorHandling:Boolean=false,
                                           bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                           successBody : (data:T) -> Unit){
    liveData.observe(this as LifecycleOwner, Observer { dataHolder ->
        handleDataHolderResult(showDialogsInFragment,
            dataHolder,
            errorBody,
            errorButtonClick,
            bypassErrorHandling,
            bypassDisableCurrentPopupOnSuccess,
            successBody)
    })
}