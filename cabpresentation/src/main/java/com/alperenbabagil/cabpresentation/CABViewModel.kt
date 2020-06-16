package com.alperenbabagil.cabpresentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.cabdomain.model.DataHolder
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
    liveData: MutableLiveData<DataHolder<ResType>>?=null,
    singleInteractor: Interactor.SingleInteractor<ParamType, ResType>,
    params: ParamType,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    setLoadingTrue: Boolean = true,
    loadingCancellable: Boolean = false,
    crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }
) : Job =
    execInteractorCore(liveData = liveData,
        dispatcher = dispatcher,
        setLoadingTrue = setLoadingTrue,
        loadingCancellable = loadingCancellable,
        interceptorBlock = interceptorBlock,
        execMethod = {
            singleInteractor.execute(params)
        }
    )

//for single retrieve interactors
inline fun <reified ResType : Any> CABViewModel.execInteractor(liveData: MutableLiveData<DataHolder<ResType>>?=null,
                                                  singleRetriveInteractor: Interactor.SingleRetrieveInteractor<ResType>,
                                                  dispatcher: CoroutineDispatcher = Dispatchers.IO,
                                                  setLoadingTrue: Boolean = true,
                                                  loadingCancellable: Boolean = false,
                                                  crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }) : Job =

    execInteractorCore(liveData = liveData,
        dispatcher = dispatcher,
        setLoadingTrue = setLoadingTrue,
        loadingCancellable = loadingCancellable,
        interceptorBlock = interceptorBlock,
        execMethod = {
            singleRetriveInteractor.execute()
        }
    )

inline fun <reified ResType : Any> CABViewModel.execInteractorCore(
    liveData: MutableLiveData<DataHolder<ResType>>?=null,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    setLoadingTrue: Boolean = true,
    loadingCancellable: Boolean = false,
    crossinline execMethod: suspend () -> DataHolder<ResType>,
    crossinline interceptorBlock: (dataHolder: DataHolder<ResType>) -> Boolean = { _ -> false }
): Job {
    val loadingDataHolder = DataHolder.Loading(cancellable = loadingCancellable)
    val loadingUUID = loadingDataHolder.tag
    if (setLoadingTrue) liveData?.value = loadingDataHolder
    val job = (this as ViewModel).viewModelScope.launch(dispatcher) {
        val resultOfExecute = try {
            execMethod.invoke()
        } catch (e: Exception) {
            DataHolder.Fail()
        }
        jobMap.remove(loadingUUID)
        if (!interceptorBlock.invoke(resultOfExecute))
            liveData?.postValue(resultOfExecute)
    }
    jobMap[loadingUUID] = job
    return job
}

fun <T> MutableLiveData<T>.asLiveData():LiveData<T> = this as LiveData<T>

