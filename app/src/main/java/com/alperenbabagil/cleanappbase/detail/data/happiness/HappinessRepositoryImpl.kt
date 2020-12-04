package com.alperenbabagil.cleanappbase.detail.data.happiness

import com.alperenbabagil.cabdata.DataSource
import com.alperenbabagil.cleanappbase.core.data.BaseFlowDataSource
import com.alperenbabagil.cleanappbase.detail.domain.happiness.HappinessRepository
import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.dataholder.DataHolder
import kotlinx.coroutines.flow.Flow

class HappinessRepositoryImpl(private val happinessDataSource:
                              BaseFlowDataSource<ProfileDetail, HappinessResult>
) : HappinessRepository {
    override suspend fun getHappiness(profileDetail: ProfileDetail): Flow<DataHolder<HappinessResult>> {
        return happinessDataSource.getDataSourceResult(profileDetail)
    }
}