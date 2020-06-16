package com.alperenbabagil.cleanappbase.core.presentation

import androidx.lifecycle.ViewModel
import com.alperenbabagil.cabpresentation.CABViewModel
import kotlinx.coroutines.Job

abstract class CABDemoBaseViewModel : ViewModel(),CABViewModel {
    override val jobMap: HashMap<String, Job> = HashMap()
}