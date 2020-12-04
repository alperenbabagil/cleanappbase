package com.alperenbabagil.cleanappbase.detail.domain.happiness

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.cleanappbase.core.domain.model.BaseSingleFlowInteractor
import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.dataholder.DataHolder
import kotlinx.coroutines.flow.Flow

class HappinessInteractor(private val happinessRepository: HappinessRepository) :
    BaseSingleFlowInteractor<HappinessInteractor.Params, HappinessResult>() {
    data class Params(val profileDetail: ProfileDetail) : Interactor.Params()

    override suspend fun executeInteractor(params: Params): Flow<DataHolder<HappinessResult>> {
        return happinessRepository.getHappiness(params.profileDetail)
    }
}