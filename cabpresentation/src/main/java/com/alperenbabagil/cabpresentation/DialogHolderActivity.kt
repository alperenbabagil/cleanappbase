package com.alperenbabagil.cabpresentation

import android.app.Dialog

interface DialogHolderActivity {

    var currentDialog : Dialog?

    var isFullScreenNow : Boolean

    fun loadingDialogDismissed()

    val cabViewModel : CABViewModel?
}