package com.alperenbabagil.cabpresentation.navigation

import android.content.Context
import android.content.Intent
import java.lang.Exception

class ClassLoader {

    fun <T>loadClassOrNull(className:String) : Class<out T>?{
        val generatedClass= try{
            Class.forName(className)
        }catch (e:Exception) {
            null
        }
        return generatedClass?.castOrNull()
    }

    private inline fun <reified T : Any>Any.castOrNull() = this as? T
}