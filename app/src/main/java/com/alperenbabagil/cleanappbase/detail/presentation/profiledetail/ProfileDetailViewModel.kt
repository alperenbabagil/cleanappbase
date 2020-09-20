package com.alperenbabagil.cleanappbase.detail.presentation.profiledetail

import androidx.lifecycle.MutableLiveData
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cabpresentation.asLiveData
import com.alperenbabagil.cabpresentation.execInteractor
import com.alperenbabagil.cabpresentation.execInteractorCore
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseViewModel
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.GetProfileDetailInteractor
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail

class ProfileDetailViewModel(private val getProfileDetailInteractor:
                             BaseSingleInteractor<GetProfileDetailInteractor.Params, ProfileDetail>) :
    CABDemoBaseViewModel() {

    private val _profileDetailLiveData = MutableLiveData<DataHolder<ProfileDetail>>()
    val profileDetailLiveData = _profileDetailLiveData.asLiveData()

    fun getProfileDetail(userId:String,requestResultType: RequestResultType,delay:Long){
        execInteractor(_profileDetailLiveData,
            getProfileDetailInteractor,
            GetProfileDetailInteractor.Params(userId,requestResultType,delay)
            )
    }
}