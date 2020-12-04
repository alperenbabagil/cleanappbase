package com.alperenbabagil.cleanappbase.detail.presentation

import androidx.lifecycle.MutableLiveData
import com.alperenbabagil.cabpresentation.asLiveData
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseViewModel
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.dataholder.DataHolder

class DetailViewModel : CABDemoBaseViewModel(){

    private val _sharedProfileDetailLiveData = MutableLiveData<DataHolder<ProfileDetail>>(DataHolder.Loading())
    val sharedProfileDetailLiveData = _sharedProfileDetailLiveData.asLiveData()


    fun setCurrentProfileDetail(profileDetailDH : DataHolder<ProfileDetail>){
        _sharedProfileDetailLiveData.postValue(profileDetailDH)
    }
}