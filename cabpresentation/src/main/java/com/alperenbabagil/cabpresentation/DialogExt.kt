package com.alperenbabagil.cabpresentation

import android.app.Activity
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.simpleanimationpopuplibrary.SapDialog

fun DialogHolderActivity.showLoadingDialog(forceCancellable:Boolean=false,
                                           dataHolder: DataHolder.Loading?=null,
                                           cancelledCallback: () -> Unit ={}
                               ){
    (this as? Activity)?.let {
        currentDialog?.dismiss()
        currentDialog= SapDialog(it).apply {
            isFullScreen=isFullScreenNow
            isOnlyAnimation=true
            isCancellable=dataHolder?.cancellable ?:false
            if(forceCancellable) isCancellable=true
            if(isCancellable){
                addDismissEvent {
                    loadingDialogDismissed()
                    dataHolder?.let { dataHolderLoading->
                        cabViewModel?.loadingCancelled(dataHolderLoading.tag)
                    }
                    cancelledCallback.invoke()
                }
            }
        }.build().show()
    }
}

fun DialogHolderActivity.showLoadingDialogWithText(@StringRes stringResId: Int?=null,
                                                   loadingString: String?=
                                                           (this as? Activity)?.getString(R.string.loading)?:"Loading"
                                                   ,
                                                   isCancellable: Boolean=true,
                                                   infoPopupButtonClick : () -> Unit = {}){

    (this as? Activity)?.let{
        currentDialog?.dismiss()
        currentDialog= SapDialog(this).apply {
            animResource=R.raw.sap_loading_anim
            messageText=stringResId?.let { getString(it) } ?: loadingString
            this.isCancellable=isCancellable
            if(isCancellable){
                addPositiveButton(getString(R.string.cancel)){this.cancel()}
                addCancelEvent(infoPopupButtonClick)
            }
        }.build().show()
    }

}


fun DialogHolderActivity.showErrorDialog(@StringRes stringResId: Int?=null,
                                         errorString: String=
                                                 (this as? Activity)?.
                                                 getString(R.string.error)?:"Error",
                                         finishActivityOnClick:Boolean=false,
                                         errorButtonClick : () -> Unit = {}){
    (this as? Activity)?.let{
        currentDialog?.dismiss()
        currentDialog= SapDialog(this).apply {
            isFullScreen=isFullScreenNow
            titleText=getString(R.string.error)
            messageText=stringResId?.let { getString(it) } ?: errorString
            isCancellable=false
            addPositiveButton(getString(R.string.ok)){
                if(finishActivityOnClick) finish()
                else errorButtonClick.invoke()
            }
        }.build().show()
    }
}

fun DialogHolderActivity.showInfoDialog(infoString: String?=null, infoPopupButtonClick : () -> Unit = {}){
    (this as? Activity)?.let{
        currentDialog?.dismiss()
        currentDialog=SapDialog(this).apply {
            isFullScreen=isFullScreenNow
            titleText=getString(R.string.info)
            animResource= R.raw.info_anim
            messageText=infoString
            isCancellable=false
            addPositiveButton(getString(R.string.ok)){infoPopupButtonClick.invoke()}
        }.build().show()
    }
}

fun DialogHolderActivity.showWarningDialog(infoString: String?=null, infoPopupButtonClick : () -> Unit = {}){
    (this as? Activity)?.let{
        currentDialog?.dismiss()
        currentDialog=SapDialog(this).apply {
            isFullScreen=isFullScreenNow
            titleText=getString(R.string.info)
            animResource= R.raw.warning_anim
            messageText=infoString
            isCancellable=false
            addPositiveButton(getString(R.string.ok)){infoPopupButtonClick.invoke()}
        }.build().show()
    }
}

fun DialogHolderActivity.showDoneDialog(infoString: String?=null, infoPopupButtonClick : () -> Unit = {}){
    (this as? Activity)?.let{
        currentDialog?.dismiss()
        currentDialog=SapDialog(this).apply {
            isFullScreen=isFullScreenNow
            titleText=getString(R.string.done)
            animResource= R.raw.done_anim
            messageText=infoString
            isCancellable=false
            addPositiveButton(getString(R.string.ok)){infoPopupButtonClick.invoke()}
        }.build().show()
    }
}



fun DialogHolderActivity.dismissCurrentDialog(){
    currentDialog?.dismiss()
}

fun DialogHolderActivity.showOptionsDialog(charSequence: Array<CharSequence>,
                                           title: String,
                                           optionSelectedCallback: (index:Int) -> Unit) {
    (this as? Activity)?.let{
        currentDialog?.dismiss()
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setItems(charSequence){_,index->
            optionSelectedCallback.invoke(index)
        }
        currentDialog = builder.show()
    }
}

fun DialogHolderActivity.showYesNoDialog(message: String,
                   onYesClicked: ()->Unit) {
    (this as? Activity)?.let{
        currentDialog?.dismiss()
        currentDialog=SapDialog(this).apply {
            messageText=message
            animResource=R.raw.question_mark
            addPositiveButton(getString(R.string.yes)){
                onYesClicked.invoke()
            }
            addNegativeButton(getString(R.string.no)){}
        }.build().show()
    }
}

