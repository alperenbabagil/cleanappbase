package com.alperenbabagil.cabpresentation

import android.view.View
import androidx.databinding.BindingAdapter

fun View.show(){
    this.visibility= View.VISIBLE
}

fun View.hide(){
    this.visibility= View.GONE
}

@BindingAdapter("viewVisibility")
fun View.setVisibility(isVisible: Boolean?){
    isVisible?.let {
        if(it) this.show() else this.hide()
    }
}
