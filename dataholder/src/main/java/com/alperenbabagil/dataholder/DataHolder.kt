package com.alperenbabagil.dataholder

import com.alperenbabagil.dataholder.CoreDomainConstants.Companion.DEFAULT_ERROR_STR
import com.alperenbabagil.dataholder.CoreDomainConstants.Companion.DEFAULT_LOADING_STR
import java.util.*


sealed class DataHolder<out T : Any> {

    data class Success<out T : Any>(val data: T) : DataHolder<T>(){
        var isObserved = false
            private set

        fun setObserved(){
            isObserved=true
        }
    }

    data class Fail(
        val errorResourceId: Int? = null,
        val errStr: String = DEFAULT_ERROR_STR,
        val error: BaseError? = null,
        val failType: FailType = FailType.ERROR,
        val cancellable: Boolean = false
    ) : DataHolder<Nothing>(){
        var isObserved = false
            private set

        fun setObserved(){
            isObserved=true
        }
    }

    data class Loading(
        val loadingResourceId: Int? = null,
        val loadingStr: String = DEFAULT_LOADING_STR,
        var cancellable: Boolean = false,
        var progress: Int = 0,
        var tag: String = UUID.randomUUID().toString()
    ) : DataHolder<Nothing>()
}

fun <T : Any, R : Any> DataHolder<T>.handleSuccess(
    successBlock:
        (dataHolder: DataHolder.Success<T>) -> R
): DataHolder<R> = when (this) {
    is DataHolder.Success<T> -> DataHolder.Success(successBlock.invoke(this))
    is DataHolder.Fail -> this
    is DataHolder.Loading -> this
}

suspend fun <T : Any, R : Any> DataHolder<T>.handleSuccess(
    successBlock:
        suspend (dataHolder: DataHolder.Success<T>) -> R
): DataHolder<R> = when (this) {
    is DataHolder.Success<T> -> DataHolder.Success(successBlock.invoke(this))
    is DataHolder.Fail -> this
    is DataHolder.Loading -> this
}
