package com.alperenbabagil.cabdomain.model

import com.alperenbabagil.cabdomain.CoreDomainConstants.Companion.DEFAULT_ERROR_STR
import com.alperenbabagil.cabdomain.CoreDomainConstants.Companion.DEFAULT_LOADING_STR
import java.util.*


sealed class DataHolder<out T: Any> {

    data class Success<out T : Any>(val data:T) : DataHolder<T>()

    data class Fail(val errorResourceId : Int?= null,
                    val errStr: String=DEFAULT_ERROR_STR,
                    val error: BaseError?=null
                    ) : DataHolder<Nothing>()

    data class Loading(val loadingResourceId : Int?=null,
                       val loadingStr: String=DEFAULT_LOADING_STR,
                       var cancellable:Boolean=false,
                       var progress: Int=0,
                       var tag:String=UUID.randomUUID().toString()) : DataHolder<Nothing>()

}