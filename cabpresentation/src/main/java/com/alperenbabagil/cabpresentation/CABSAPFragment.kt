package com.alperenbabagil.cabpresentation

import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.simpleanimationpopuplibrary.showErrorDialog

interface CABSAPFragment : DialogHolderFragment {

}

fun <T : Any>CABSAPFragment.handleDataHolderResult(showDialogsInFragment:Boolean=true,
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
                if(!bypassDisableCurrentPopupOnSuccess) currentDialogView?.hide()
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

fun <T : Any>CABSAPFragment.observeDataHolder(showDialogsInFragment:Boolean=true,
                                              liveData: LiveData<DataHolder<T>>,
                                              errorBody : (errorStr:String,errorResId:Int) -> Unit = {_,_ -> },
                                              errorButtonClick : () -> Unit = {},
                                              bypassErrorHandling:Boolean=false,
                                              bypassDisableCurrentPopupOnSuccess:Boolean=false,
                                              successBody : (data:T) -> Unit){
    liveData.observe(this as LifecycleOwner, { dataHolder ->
        handleDataHolderResult(showDialogsInFragment,
            dataHolder,
            errorBody,
            errorButtonClick,
            bypassErrorHandling,
            bypassDisableCurrentPopupOnSuccess,
            successBody)
    })
}