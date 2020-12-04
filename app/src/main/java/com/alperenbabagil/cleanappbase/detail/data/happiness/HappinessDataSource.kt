package com.alperenbabagil.cleanappbase.detail.data.happiness

import com.alperenbabagil.cabdata.DataSource
import com.alperenbabagil.cleanappbase.core.data.BaseFlowDataSource
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.dataholder.DataHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

@ExperimentalCoroutinesApi
class HappinessDataSource(private val happinessGenerator: HappinessGenerator) :
    BaseFlowDataSource<ProfileDetail,HappinessResult>() {

    override suspend fun getDataSourceResult(request: ProfileDetail): Flow<DataHolder<HappinessResult>> {
        return callbackFlow {
            happinessGenerator.generate(request){
                if(!isClosedForSend) offer(DataHolder.Success(HappinessResult(it)))
            }
            awaitClose {
                happinessGenerator.isGeneratingOn=false
            }
        }
    }
}