package com.alperenbabagil.cleanappbase.core.data

import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.cabdata.model.BaseApiResponse
import com.alperenbabagil.dataholder.BaseError
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants.Companion.SERVER_STATUS_FAIL
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants.Companion.SERVER_STATUS_SUCCESS
import com.alperenbabagil.cleanappbase.core.data.model.CABDemoBaseApiResponse
import retrofit2.Response

class CABDemoApiCallAdapter : ApiCallAdapter {

    override suspend fun <T : Any> adapt(serviceBody: suspend () -> Response<out BaseApiResponse<T>>?):
            com.alperenbabagil.dataholder.DataHolder<T> {
        return try {
            serviceBody.invoke()?.let {
                if(it.isSuccessful){
                    it.body()?.let {
                        (it as? CABDemoBaseApiResponse<T>)?.let {
                            when(it.status){
                                SERVER_STATUS_SUCCESS -> com.alperenbabagil.dataholder.DataHolder.Success(it.data)
                                SERVER_STATUS_FAIL ->{
                                    it.serverError?.let {
                                        com.alperenbabagil.dataholder.DataHolder.Fail(errStr = it.errorMessage,error = it)
                                    }?: com.alperenbabagil.dataholder.DataHolder.Fail()
                                }
                                else -> com.alperenbabagil.dataholder.DataHolder.Fail()
                            }
                        }
                    } ?: com.alperenbabagil.dataholder.DataHolder.Fail()
                }
                else{
                    com.alperenbabagil.dataholder.DataHolder.Fail()
                }
            }?: com.alperenbabagil.dataholder.DataHolder.Fail()
        }
        catch (e:Exception){
            com.alperenbabagil.dataholder.DataHolder.Fail(error = com.alperenbabagil.dataholder.BaseError(
                e
            )
            )
        }
    }
}