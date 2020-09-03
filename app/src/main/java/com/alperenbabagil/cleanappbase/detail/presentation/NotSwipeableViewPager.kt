package com.alperenbabagil.cleanappbase.detail.presentation

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NotSwipeableViewPager(context: Context, attributeSet: AttributeSet) : ViewPager(context,attributeSet) {

    var isPagingEnabled = false   //Modify this in code for switch

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(event)
    }
}