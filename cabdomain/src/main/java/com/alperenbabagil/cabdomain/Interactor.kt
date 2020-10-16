package com.alperenbabagil.cabdomain

import com.alperenbabagil.dataholder.DataHolder
import kotlinx.coroutines.Deferred

interface Interactor {

    interface DeferredInteractor<params : Params, T : Any> : Interactor {
        suspend fun execute(postParams: params): Deferred<DataHolder<T>>
    }

    interface DeferredRetrieveInteractor<T : Any> : Interactor {
        suspend fun execute(): Deferred<DataHolder<T>>
    }

    interface SingleInteractor<params : Params, T : Any> : Interactor {
        suspend fun execute(params: params): DataHolder<T>
    }

    interface SingleRetrieveInteractor<T : Any> : Interactor{
        suspend fun execute(): DataHolder<T>
    }

    interface SingleSyncInteractor<params : Params, T : Any> : Interactor {
        fun execute(params: params): DataHolder<T>
    }

    interface SingleSyncRetrieveInteractor<T : Any> : Interactor{
        fun execute(): DataHolder<T>
    }

    interface SinglePlainRetrieveInteractor<T : Any?> : Interactor{
        suspend fun execute(): T
    }

    interface SingleSyncPlainRetrieveInteractor<T : Any?> : Interactor{
        fun execute(): T
    }

    abstract class Params {
        // marker class
    }
}