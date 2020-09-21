package com.alperenbabagil.cleanappbase.detail.domain.profiledetail

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import kotlinx.coroutines.delay

class GetProfileDetailInteractor(private val profileDetailRepository: ProfileDetailRepository) :
    BaseSingleInteractor<GetProfileDetailInteractor.Params, ProfileDetail>()
{
    data class Params(val userId:String,
                 val requestResultType: RequestResultType,
                 val delayMsecs:Long=2000
                 ) : Interactor.Params()

    override suspend fun executeInteractor(params: Params): com.alperenbabagil.dataholder.DataHolder<ProfileDetail> {
        delay(params.delayMsecs)
        return profileDetailRepository.getProfileDetail(params.userId,
            params.requestResultType)
    }
}