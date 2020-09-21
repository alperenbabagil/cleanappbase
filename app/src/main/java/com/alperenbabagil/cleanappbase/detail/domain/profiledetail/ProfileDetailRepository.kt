package com.alperenbabagil.cleanappbase.detail.domain.profiledetail

import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail

interface ProfileDetailRepository {

    suspend fun getProfileDetail(userId:String,
                                 requestResultType: RequestResultType) :
            com.alperenbabagil.dataholder.DataHolder<ProfileDetail>
}