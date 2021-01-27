package com.alperenbabagil.cabpresentation

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.dataholder.FailType

interface CABActivity : DialogHost {
    override fun <T : Any> getInterceptorLambda(): ((dataHolder: DataHolder<T>) -> Boolean)? {
        return null
    }
}

fun <T : Any> CABActivity.handleDataHolderResult(
    dataHolder: DataHolder<T>,
    errorBody: ((DataHolder.Fail) -> Unit)? = null,
    errorButtonClick: ((DataHolder.Fail) -> Unit)? = null,
    bypassErrorHandling: Boolean = false,
    bypassDisableCurrentPopupOnSuccess: Boolean = false,
    observeSuccessValueOnce: Boolean = false,
    interceptor: ((dataHolder: DataHolder<T>) -> Boolean)?=null,
    observeFailValueOnce: Boolean = false,
    showLoadingDialog:Boolean=true,
    successBody: ((data: T) -> Unit)? = null

) {
    if(interceptor?.invoke(dataHolder)==true) return
    if (dataHolder is DataHolder.Success) {
        if (observeSuccessValueOnce && dataHolder.isObserved) return
        if (!bypassDisableCurrentPopupOnSuccess) dismissCurrentDialog()
        successBody?.invoke(dataHolder.data)
        dataHolder.setObserved()
    }

    if (dataHolder is DataHolder.Fail) {
        if (observeFailValueOnce && dataHolder.isObserved) return
        dismissCurrentDialog()
        if (bypassErrorHandling) {
            errorBody?.invoke(dataHolder)
        } else {
            when (dataHolder.failType) {
                FailType.ERROR -> {
                    showErrorDialog(
                        titleRes = R.string.error,
                        errorRes = dataHolder.errorResourceId ?: -1,
                        errorStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = {
                            errorButtonClick?.invoke(dataHolder)
                        },
                        isCancellable = dataHolder.cancellable
                    )
                }
                FailType.INFO -> {
                    showInfoDialog(
                        titleRes = R.string.info,
                        infoRes = dataHolder.errorResourceId ?: -1,
                        infoStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = {
                            errorButtonClick?.invoke(dataHolder)
                        },
                        isCancellable = dataHolder.cancellable
                    )
                }
                FailType.WARNING -> {
                    showWarningDialog(
                        titleRes = R.string.warning,
                        warningRes = dataHolder.errorResourceId ?: -1,
                        warningStr = dataHolder.errStr,
                        positiveButtonStrRes = R.string.ok,
                        positiveButtonClick = {
                            errorButtonClick?.invoke(dataHolder)
                        },
                        isCancellable = dataHolder.cancellable
                    )
                }
            }
        }
        dataHolder.setObserved()
    }
    if (dataHolder is DataHolder.Loading) {
        if (showLoadingDialog) {
            dismissCurrentDialog()
            showLoadingDialog(dataHolder = dataHolder)
        }
    }
}

fun <T : Any> CABActivity.observeDataHolder(
    liveData: LiveData<DataHolder<T>>,
    errorBody: ((DataHolder.Fail) -> Unit)? = null,
    errorButtonClick: ((DataHolder.Fail) -> Unit)? = null,
    bypassErrorHandling: Boolean = false,
    bypassDisableCurrentPopupOnSuccess: Boolean = false,
    observeSuccessValueOnce: Boolean = false,
    interceptor: ((dataHolder: DataHolder<T>) -> Boolean)?=null,
    observeFailValueOnce: Boolean = false,
    showLoadingDialog:Boolean=true,
    successBody: ((data: T) -> Unit)? = null
) {
    liveData.observe(this as LifecycleOwner, { dataHolder ->
        handleDataHolderResult(
            dataHolder,
            errorBody,
            errorButtonClick,
            bypassErrorHandling,
            bypassDisableCurrentPopupOnSuccess,
            observeSuccessValueOnce,
            interceptor,
            observeFailValueOnce,
            showLoadingDialog,
            successBody
        )
    })
}