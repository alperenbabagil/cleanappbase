package com.alperenbabagil.cleanappbase.core.domain

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.DataHolder

abstract class BaseSingleInteractor<P: Interactor.Params,T : Any>() : Interactor.SingleInteractor<P,T> {
    override suspend fun execute(params: P): DataHolder<T> {
        // to intercept execute
        return executeInteractor(params)
    }

    abstract suspend fun executeInteractor(params: P): DataHolder<T>
}