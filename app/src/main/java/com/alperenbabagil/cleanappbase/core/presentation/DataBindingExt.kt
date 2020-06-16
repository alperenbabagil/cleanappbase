package com.alperenbabagil.cleanappbase.core.presentation

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.api.load
import coil.transform.CircleCropTransformation
import com.alperenbabagil.cleanappbase.R
import java.text.DateFormat
import java.util.*

@BindingAdapter("srcUrl")
fun ImageView.loadCircleImageFromUrl(url: String?){
    url?.let {
        load(url) {
            crossfade(true)
            placeholder(R.drawable.ic_launcher_foreground)
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("dateField")
fun TextView.setDate(date: Date?){
    date?.let {
        text=DateFormat.getDateInstance(DateFormat.SHORT).format(date)
    }
}

