package com.alperenbabagil.cleanappbase.core.data

import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.cabdata.model.BaseApiResponse
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants.Companion.SERVER_STATUS_FAIL
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants.Companion.SERVER_STATUS_SUCCESS
import com.alperenbabagil.cleanappbase.core.data.model.CABDemoBaseApiResponse
import com.alperenbabagil.dataholder.BaseError
import com.alperenbabagil.dataholder.DataHolder
import retrofit2.Response

class CABDemoApiCallAdapter : ApiCallAdapter {

    override suspend fun <T : Any> adapt(serviceBody: suspend () -> Response<out BaseApiResponse<T>>?):
            DataHolder<T> {
        return try {
            serviceBody.invoke()?.let {
                if(it.isSuccessful){
                    it.body()?.let {
                        (it as? CABDemoBaseApiResponse<T>)?.let {
                            when(it.status){
                                SERVER_STATUS_SUCCESS -> DataHolder.Success(it.data)
                                SERVER_STATUS_FAIL ->{
                                    it.serverError?.let {
                                        DataHolder.Fail(errStr = it.errorMessage,error = it)
                                    }?: DataHolder.Fail()
                                }
                                else -> DataHolder.Fail()
                            }
                        }
                    } ?: DataHolder.Fail()
                }
                else{
                    DataHolder.Fail()
                }
            }?: DataHolder.Fail()
        }
        catch (e:Exception){
            DataHolder.Fail(error = BaseError(
                e
            )
            )
        }
    }
}