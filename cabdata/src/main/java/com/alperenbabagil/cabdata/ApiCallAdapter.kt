package com.alperenbabagil.cabdata

import com.alperenbabagil.cabdata.model.BaseApiResponse
import com.alperenbabagil.dataholder.DataHolder
import retrofit2.Response

interface ApiCallAdapter {
    suspend fun <T : Any> adapt(serviceBody: suspend () -> Response<out BaseApiResponse<T>>?) : DataHolder<T>
}