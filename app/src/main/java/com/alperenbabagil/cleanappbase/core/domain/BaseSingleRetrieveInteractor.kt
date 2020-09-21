package com.alperenbabagil.cleanappbase.core.domain

import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.DataHolder

abstract class BaseSingleRetrieveInteractor<T : Any>() : Interactor.SingleRetrieveInteractor<T> {
    override suspend fun execute(): com.alperenbabagil.dataholder.DataHolder<T> {
        // to intercept execute
        return executeInteractor()
    }

    abstract suspend fun executeInteractor(): com.alperenbabagil.dataholder.DataHolder<T>
}