package com.alperenbabagil.cleanappbase.detail.domain.profiledetail

import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail

interface ProfileDetailRepository {

    suspend fun getProfileDetail(userId:String,
                                 requestResultType: RequestResultType) :
            DataHolder<ProfileDetail>
}