package com.alperenbabagil.cleanappbase.core.data

import com.alperenbabagil.cabdata.DataSource
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import kotlinx.coroutines.delay

abstract class BaseDataSource<R,T : Any> : DataSource.AsyncDataSource.RequestDataSource<R,T> {
    override suspend fun getResult(request: R): DataHolder<T> {
        //to intercept
        delay(2000)
        return getDataSourceResult(
            BaseRequest(
                request
            )
        )
    }

    abstract suspend fun getDataSourceResult(request: BaseRequest<R>) : DataHolder<T>
}