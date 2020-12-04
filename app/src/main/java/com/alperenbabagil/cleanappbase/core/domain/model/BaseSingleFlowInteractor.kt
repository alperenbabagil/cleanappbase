package com.alperenbabagil.cleanappbase.core.domain.model

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.DataHolder
import kotlinx.coroutines.flow.Flow

abstract class BaseSingleFlowInteractor<P: Interactor.Params,T : Any>() : Interactor.SingleFlowInteractor<P,T> {
    override suspend fun execute(params: P): Flow<DataHolder<T>> {
        // to intercept execute
        return executeInteractor(params)
    }

    abstract suspend fun executeInteractor(params: P): Flow<DataHolder<T>>
}