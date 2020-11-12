package com.alperenbabagil.cabpresentation

import android.app.Dialog
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.simpleanimationpopuplibrary.SapDialog

interface DialogHost {

    val dialog: Dialog?

    // return true if event is handled
    fun <T : Any>getInterceptorLambda() : ((dataHolder : DataHolder<T>) -> Boolean)?

    fun showLoadingDialog(animRes: Int = R.raw.sap_loading_anim,
                          isCancellable: Boolean = false,
                          dataHolder: DataHolder.Loading?=null,
                          sapCancelEvent: () -> Unit = {})

    fun dismissCurrentDialog()

    fun showDefaultDialog(
        titleRes: Int? = null,
        titleStr: String? = null,
        msgStr: String? = null,
        msgRes: Int? = null,
        animRes: Int? = null,
        titleColorRes: Int = com.alperenbabagil.simpleanimationpopuplibrary.R.color.colorPrimary,
        isCancellable: Boolean = false,
        autoStartAnimation: Boolean = true,
        loopAnimation: Boolean = false,
        positiveButtonStrRes: Int? = null,
        positiveButtonStr: String? = null,
        positiveButtonClick: (() -> Unit)? = null,
        negativeButtonStrRes: Int? = null,
        negativeButtonStr: String? = null,
        negativeButtonClick: (() -> Unit)? = null,
        cancelEvent :  (() -> Unit) ={}
    )

    fun showErrorDialog(
        titleRes: Int? = SapDialog.ERROR_RES_ID,
        titleStr: String? = null,
        errorStr: String? = null,
        errorRes: Int? = null,
        animRes: Int? = com.alperenbabagil.simpleanimationpopuplibrary.R.raw.sap_error_anim,
        titleColorRes: Int = SapDialog.ERROR_TITLE_COLOR_RES,
        isCancellable: Boolean = false,
        autoStartAnimation: Boolean = true,
        loopAnimation: Boolean = false,
        positiveButtonStrRes: Int? = null,
        positiveButtonStr: String? = null,
        positiveButtonClick: (() -> Unit)? = null,
        negativeButtonStrRes: Int? = null,
        negativeButtonStr: String? = null,
        negativeButtonClick: (() -> Unit)? = null,
        cancelEvent :  (() -> Unit) ={}
    )

    fun showWarningDialog(
        titleRes: Int? = SapDialog.WARNING_RES_ID,
        titleStr: String? = null,
        warningStr: String? = null,
        warningRes: Int? = null,
        animRes: Int? = com.alperenbabagil.simpleanimationpopuplibrary.R.raw.sap_warning_anim,
        titleColorRes: Int = SapDialog.WARNING_TITLE_COLOR_RES,
        isCancellable: Boolean = true,
        autoStartAnimation: Boolean = true,
        loopAnimation: Boolean = false,
        positiveButtonStrRes: Int? = null,
        positiveButtonStr: String? = null,
        positiveButtonClick: (() -> Unit)? = null,
        negativeButtonStrRes: Int? = null,
        negativeButtonStr: String? = null,
        negativeButtonClick: (() -> Unit)? = null,
        cancelEvent :  (() -> Unit) ={}
    )

    fun showInfoDialog(
        titleRes: Int? = SapDialog.INFO_RES_ID,
        titleStr: String? = null,
        infoStr: String? = null,
        infoRes: Int? = null,
        animRes: Int? = com.alperenbabagil.simpleanimationpopuplibrary.R.raw.sap_info_anim,
        titleColorRes: Int = SapDialog.INFO_TITLE_COLOR_RES,
        isCancellable: Boolean = true,
        autoStartAnimation: Boolean = true,
        loopAnimation: Boolean = false,
        positiveButtonStrRes: Int? = null,
        positiveButtonStr: String? = null,
        positiveButtonClick: (() -> Unit)? = null,
        negativeButtonStrRes: Int? = null,
        negativeButtonStr: String? = null,
        negativeButtonClick: (() -> Unit)? = null,
        cancelEvent :  (() -> Unit) ={}
    )


}