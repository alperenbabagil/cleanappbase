package com.alperenbabagil.cleanappbase.core.data

import com.alperenbabagil.cabdata.DataSource
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import kotlinx.coroutines.flow.Flow

abstract class BaseFlowDataSource<R,T : Any> : DataSource.FlowDataSource.RequestDataSource<R,T> {
    override suspend fun getResult(request: R): Flow<DataHolder<T>> {
        //to intercept
        return getDataSourceResult(
            request
        )
    }

    abstract suspend fun getDataSourceResult(request: R) : Flow<DataHolder<T>>
}