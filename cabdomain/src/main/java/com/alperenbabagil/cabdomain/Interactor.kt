package com.alperenbabagil.cabdomain

import kotlinx.coroutines.Deferred

interface Interactor {

    interface DeferredInteractor<params : Params, T : Any> : Interactor {
        suspend fun execute(postParams: params): Deferred<com.alperenbabagil.dataholder.DataHolder<T>>
    }

    interface DeferredRetrieveInteractor<T : Any> : Interactor {
        suspend fun execute(): Deferred<com.alperenbabagil.dataholder.DataHolder<T>>
    }

    interface SingleInteractor<params : Params, T : Any> : Interactor {
        suspend fun execute(params: params): com.alperenbabagil.dataholder.DataHolder<T>
    }

    interface SingleRetrieveInteractor<T : Any> : Interactor{
        suspend fun execute(): com.alperenbabagil.dataholder.DataHolder<T>
    }

    interface SingleSyncInteractor<params : Params, T : Any> : Interactor {
        suspend fun execute(params: params): com.alperenbabagil.dataholder.DataHolder<T>
    }

    interface SingleSyncRetrieveInteractor<T : Any> : Interactor{
        suspend fun execute(): com.alperenbabagil.dataholder.DataHolder<T>
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