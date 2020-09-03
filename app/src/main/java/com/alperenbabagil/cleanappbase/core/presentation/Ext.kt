package com.alperenbabagil.cleanappbase.core.presentation

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.text.DateFormat
import java.util.*

inline fun <T> Activity.getExtra(key:String,
                                 onFound: (value:T) -> Unit,
                                 onNotFound: () -> Unit){
    this.intent?.extras?.let {
        if(it.containsKey(key)) onFound.invoke(it.get(key) as T)
        else onNotFound.invoke()
    }
}

inline fun <T> Fragment.getArgument(key:String,
                                    onFound: (value:T) -> Unit,
                                    onNotFound: () -> Unit){
    this.arguments?.let {
        if(it.containsKey(key)) onFound.invoke(it.get(key) as T)
        else onNotFound.invoke()
    }
}

fun <T : Fragment> Fragment.newInstance(args: Bundle?, factory: ()-> T): T{
    val fragment = factory()
    args?.let {
        fragment.arguments = it
    }
    return fragment
}
fun Date.toPrettyString() : String =
    DateFormat.getDateInstance(DateFormat.SHORT).format(this)

