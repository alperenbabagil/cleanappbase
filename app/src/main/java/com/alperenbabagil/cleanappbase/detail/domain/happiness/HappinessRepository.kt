package com.alperenbabagil.cleanappbase.detail.domain.happiness

import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.dataholder.DataHolder
import kotlinx.coroutines.flow.Flow

interface HappinessRepository {

    suspend fun getHappiness(profileDetail: ProfileDetail) : Flow<DataHolder<HappinessResult>>
}