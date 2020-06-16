package com.alperenbabagil.cabpresentation.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle

class IntentLoader(){

    fun loadIntentOrReturnNull(context : Context, fullClassName:String): Intent? =
        try {
            Intent(Intent.ACTION_VIEW).setClass(context, Class.forName(fullClassName))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }

    fun loadIntentWithExtrasOrReturnNull(context : Context,fullClassName: String,extras: Bundle): Intent? =
        try {
            Intent(Intent.ACTION_VIEW).apply { putExtras(extras) }.setClass(context, Class.forName(fullClassName))
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }
}



