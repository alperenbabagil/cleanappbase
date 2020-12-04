package com.alperenbabagil.cleanappbase.detail.presentation.happiness

import com.alperenbabagil.cabpresentation.execFlowInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.BaseSingleFlowInteractor
import com.alperenbabagil.cleanappbase.core.presentation.CABDemoBaseViewModel
import com.alperenbabagil.cleanappbase.detail.domain.happiness.HappinessInteractor
import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail

class HappinessViewModel(private val happinessInteractor:
                         BaseSingleFlowInteractor<HappinessInteractor.Params,
                                 HappinessResult>) : CABDemoBaseViewModel() {


    fun getHappiness(profileDetail: ProfileDetail)
        = execFlowInteractor(singleFlowInteractor = happinessInteractor,
            params = HappinessInteractor.Params(profileDetail))
}