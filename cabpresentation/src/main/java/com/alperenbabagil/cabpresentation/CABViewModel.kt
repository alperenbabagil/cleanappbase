package com.alperenbabagil.cabpresentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperenbabagil.cabdomain.Interactor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

interface CABViewModel{
    val jobMap : HashMap<String, Job>
}

fun CABViewModel.loadingCancelled(loadingDataHolderTag: String) {
    jobMap.apply {
        this[loadingDataHolderTag]?.cancel()
        remove(loadingDataHolderTag)
    }
}

//for single interactors
inline fun <reified ResType : Any, reified ParamType : Interactor.Params> CABViewModel.execInteractor(
    liveData: MutableLiveData<com.alperenbabagil.dataholder.DataHolder<ResType>>?=null,
    singleInteractor: Interactor.SingleInteractor<ParamType, ResType>,
    params: ParamType,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    setLoadingTrue: Boolean = true,
    loadingCancellable: Boolean = false,
    loadingUUID: String?=null,
    crossinline interceptorBlock: (dataHolder: com.alperenbabagil.dataholder.DataHolder<ResType>) -> Boolean = { _ -> false }
) : Job =
    execInteractorCore(liveData = liveData,
        dispatcher = dispatcher,
        setLoadingTrue = setLoadingTrue,
        loadingCancellable = loadingCancellable,
        interceptorBlock = interceptorBlock,
        loadingUUID = loadingUUID,
        execMethod = {
            singleInteractor.execute(params)
        }
    )

//for single retrieve interactors
inline fun <reified ResType : Any> CABViewModel.execInteractor(liveData: MutableLiveData<com.alperenbabagil.dataholder.DataHolder<ResType>>?=null,
                                                               singleRetrieveInteractor: Interactor.SingleRetrieveInteractor<ResType>,
                                                               dispatcher: CoroutineDispatcher = Dispatchers.IO,
                                                               setLoadingTrue: Boolean = true,
                                                               loadingCancellable: Boolean = false,
                                                               loadingUUID: String?=null,
                                                               crossinline interceptorBlock: (dataHolder: com.alperenbabagil.dataholder.DataHolder<ResType>) -> Boolean = { _ -> false }) : Job =

    execInteractorCore(liveData = liveData,
        dispatcher = dispatcher,
        setLoadingTrue = setLoadingTrue,
        loadingCancellable = loadingCancellable,
        interceptorBlock = interceptorBlock,
        loadingUUID = loadingUUID,
        execMethod = {
            singleRetrieveInteractor.execute()
        }
    )

inline fun <reified ResType : Any> CABViewModel.execInteractorCore(
    liveData: MutableLiveData<com.alperenbabagil.dataholder.DataHolder<ResType>>?=null,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    setLoadingTrue: Boolean = true,
    loadingCancellable: Boolean = false,
    loadingUUID:String?=null,
    crossinline execMethod: suspend () -> com.alperenbabagil.dataholder.DataHolder<ResType>,
    crossinline interceptorBlock: (dataHolder: com.alperenbabagil.dataholder.DataHolder<ResType>) -> Boolean = { _ -> false }
): Job {
    val loadingDataHolder = loadingUUID?.let { com.alperenbabagil.dataholder.DataHolder.Loading(cancellable = loadingCancellable,
        tag = it) } ?: com.alperenbabagil.dataholder.DataHolder.Loading(cancellable = loadingCancellable)
    val loadingTag = loadingDataHolder.tag
    if (setLoadingTrue) liveData?.value = loadingDataHolder
    val job = (this as ViewModel).viewModelScope.launch(dispatcher) {
        val resultOfExecute = try {
            execMethod.invoke()
        } catch (e: Exception) {
            com.alperenbabagil.dataholder.DataHolder.Fail(error = com.alperenbabagil.dataholder.BaseError(
                e
            )
            )
        }
        jobMap.remove(loadingTag)
        if (!interceptorBlock.invoke(resultOfExecute))
            liveData?.postValue(resultOfExecute)
    }
    jobMap[loadingTag] = job
    return job
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this as LiveData<T>

