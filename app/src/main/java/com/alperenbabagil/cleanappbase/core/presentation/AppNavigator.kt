package com.alperenbabagil.cleanappbase.core.presentation

import android.app.Activity
import android.content.Intent
import com.alperenbabagil.cabpresentation.navigation.CABNavigator
import com.alperenbabagil.cleanappbase.detail.presentation.DetailActivity

class AppNavigator : CABNavigator {

    fun navigateToDetailPage(username: String,activity:Activity){
        activity.startActivity(Intent(activity,DetailActivity::class.java).apply{
            putExtra(USER_NAME_KEY,username)
        })
    }

    companion object{
        const val USER_NAME_KEY="USER_NAME_KEY"
    }
}