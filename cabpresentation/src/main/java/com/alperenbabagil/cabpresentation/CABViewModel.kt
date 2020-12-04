package com.alperenbabagil.cabpresentation

import androidx.lifecycle.*
import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.BaseError
import com.alperenbabagil.dataholder.DataHolder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

interface CABViewModel{
    val jobMap : HashMap<String, Job>

    data class ExecutionResult<T : Any>(val job:Job, val liveData: LiveData<DataHolder<T>>)
}

fun CABViewModel.loadingCancelled(loadingDataHolderTag: String) {
    jobMap.apply {
        this[loadingDataHolderTag]?.cancel()
        remove(loadingDataHolderTag)
    }
}

//for single interactors
inline fun <reified ResType : Any, reified ParamType : Interactor.Params> CABViewModel.execInteractor(
    liveData: MutableLiveData<DataHolder<ResType>>?=null,
    singleInteractor: Interactor.SingleInteractor<ParamType, ResType>,
    params: ParamType,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    setLoadingTrue: Boolean = true,
    loadingCancellable: Boolean = false,
    loadingUUID: String?=null,
    crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }
) : CABViewModel.ExecutionResult<ResType> =
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
inline fun <reified ResType : Any> CABViewModel.execInteractor(liveData: MutableLiveData<DataHolder<ResType>>?=null,
                                                               singleRetrieveInteractor: Interactor.SingleRetrieveInteractor<ResType>,
                                                               dispatcher: CoroutineDispatcher = Dispatchers.IO,
                                                               setLoadingTrue: Boolean = true,
                                                               loadingCancellable: Boolean = false,
                                                               loadingUUID: String?=null,
                                                               crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }) : CABViewModel.ExecutionResult<ResType> =

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

//for single flow retrieve interactors
inline fun <reified ResType : Any, reified ParamType : Interactor.Params> CABViewModel.execFlowInteractor(liveData: MutableLiveData<DataHolder<ResType>>?=null,
                                                                   singleFlowInteractor: Interactor.SingleFlowInteractor<ParamType,ResType>,
                                                                   params: ParamType,
                                                                   dispatcher: CoroutineDispatcher = Dispatchers.IO,
                                                                   setLoadingTrue: Boolean = true,
                                                                   loadingCancellable: Boolean = false,
                                                                   loadingUUID: String?=null,
                                                                   noinline getFlow: ((flow: Flow<DataHolder<ResType>>) -> Unit)?=null,
                                                                   crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }) : CABViewModel.ExecutionResult<ResType> =

    execFlowInteractorCore(liveData = liveData,
        dispatcher = dispatcher,
        setLoadingTrue = setLoadingTrue,
        loadingCancellable = loadingCancellable,
        loadingUUID = loadingUUID,
        execMethod = {finalDataHolder->
            getFlow?.invoke(singleFlowInteractor.execute(params)) ?: kotlin.run {
                singleFlowInteractor.execute(params).collect {
                    if(!interceptorBlock.invoke(it))
                        finalDataHolder.postValue(it)
                }
            }
        }
    )

//for single flow retrieve interactors
inline fun <reified ResType : Any> CABViewModel.execFlowRetrieveInteractor(liveData: MutableLiveData<DataHolder<ResType>>?=null,
                                                                   singleRetrieveFlowInteractor: Interactor.SingleFlowRetrieveInteractor<ResType>,
                                                                   dispatcher: CoroutineDispatcher = Dispatchers.IO,
                                                                   setLoadingTrue: Boolean = true,
                                                                   loadingCancellable: Boolean = false,
                                                                   loadingUUID: String?=null,
                                                                   noinline getFlow: ((flow: Flow<DataHolder<ResType>>) -> Unit)?=null,
                                                                   crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }) : CABViewModel.ExecutionResult<ResType> =

    execFlowInteractorCore(liveData = liveData,
        dispatcher = dispatcher,
        setLoadingTrue = setLoadingTrue,
        loadingCancellable = loadingCancellable,
        loadingUUID = loadingUUID,
        execMethod = {finalDataHolder->
            getFlow?.invoke(singleRetrieveFlowInteractor.execute()) ?: kotlin.run {
                singleRetrieveFlowInteractor.execute().collect {
                    if(!interceptorBlock.invoke(it))
                        finalDataHolder.postValue(it)
                }
            }
        }
    )

inline fun <reified ResType : Any> CABViewModel.execInteractorCore(
    liveData: MutableLiveData<DataHolder<ResType>>?=null,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    setLoadingTrue: Boolean = true,
    loadingCancellable: Boolean = false,
    loadingUUID:String?=null,
    crossinline execMethod: suspend () -> DataHolder<ResType>,
    crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }
): CABViewModel.ExecutionResult<ResType> {
    val currentLiveData = liveData ?: MutableLiveData<DataHolder<ResType>>()
    val loadingDataHolder = loadingUUID?.let { DataHolder.Loading(cancellable = loadingCancellable,
        tag = it) } ?: DataHolder.Loading(cancellable = loadingCancellable)
    val loadingTag = loadingDataHolder.tag
    if (setLoadingTrue) currentLiveData.value = loadingDataHolder
    val job = (this as ViewModel).viewModelScope.launch(dispatcher) {
        val resultOfExecute = try {
            execMethod.invoke()
        } catch (e: Exception) {
            DataHolder.Fail(error = BaseError(
                e
            )
            )
        }
        jobMap.remove(loadingTag)
        if (!interceptorBlock.invoke(resultOfExecute))
            currentLiveData.postValue(resultOfExecute)
    }
    jobMap[loadingTag] = job
    return CABViewModel.ExecutionResult(job,currentLiveData.asLiveData())
}

inline fun <reified ResType : Any> CABViewModel.execFlowInteractorCore(
    liveData: MutableLiveData<DataHolder<ResType>>?=null,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    setLoadingTrue: Boolean = true,
    loadingCancellable: Boolean = false,
    loadingUUID: String?=null,
    noinline execMethod: suspend (liveData: MutableLiveData<DataHolder<ResType>>) -> Unit,
): CABViewModel.ExecutionResult<ResType> {
    val currentLiveData = liveData ?: MutableLiveData<DataHolder<ResType>>()
    val loadingDataHolder = loadingUUID?.let { DataHolder.Loading(cancellable = loadingCancellable,
        tag = it) } ?: DataHolder.Loading(cancellable = loadingCancellable)
    val loadingTag = loadingDataHolder.tag
    if (setLoadingTrue) currentLiveData.value = loadingDataHolder
    val job = (this as ViewModel).viewModelScope.launch(dispatcher) {
        try {
            execMethod.invoke(currentLiveData)
        } catch (e: Exception) {
            currentLiveData.postValue(DataHolder.Fail(error = BaseError(
                e
            )
            ))
        }
        jobMap.remove(loadingTag)
    }
    jobMap[loadingTag] = job
    return CABViewModel.ExecutionResult(job,currentLiveData.asLiveData())
}

fun <T> MutableLiveData<T>.asLiveData(): LiveData<T> = this as LiveData<T>

