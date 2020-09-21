package com.alperenbabagil.cabpresentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alperenbabagil.dataholder.DataHolder

interface CABFragment : DialogHost {

}

fun <T : Any>CABFragment.handleDataHolderResult(showDialogsInFragment:Boolean=true,
                                                dataHolder: com.alperenbabagil.dataholder.DataHolder<T>,
                                                errorBody : (errorStr:String,errorResId:Int) -> Unit,
                                                errorButtonClick : () -> Unit,
                                                bypassErrorHandling:Boolean,
                                                bypassDisableCurrentPopupOnSuccess:Boolean,
                                                successBody : (data:T) -> Unit

){
    when(dataHolder){
        is com.alperenbabagil.dataholder.DataHolder.Success ->{
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
        is com.alperenbabagil.dataholder.DataHolder.Fail ->{
            if(bypassErrorHandling){
                errorBody.invoke(dataHolder.errStr,dataHolder.errorResourceId?: -1)
            }
            else{
                if(showDialogsInFragment){
                    showErrorDialog(
                        titleRes = R.string.error,
                        errorRes = dataHolder.errorResourceId?:-1,
                        errorStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = errorButtonClick
                    )
                }
                else{
                    ((this as? Fragment)?.requireActivity() as? CABActivity)?.
                    showErrorDialog(
                        titleRes = R.string.error,
                        errorRes = dataHolder.errorResourceId?:-1,
                        errorStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = errorButtonClick
                    )
                }
            }
        }
        is com.alperenbabagil.dataholder.DataHolder.Loading ->{
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
                                           liveData: LiveData<com.alperenbabagil.dataholder.DataHolder<T>>,
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