package com.alperenbabagil.cabpresentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.DataHolder
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import kotlinx.coroutines.Job
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class CABViewModelKtTest{

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @SpyK
    var cabViewModel = DummyViewModel()

    private val singleLiveDataValue = "singleValue"

    private val singleRetrieveLiveDataValue = "singleRetrieveValue"

    private val param = object : Interactor.Params(){}

    private val singleLiveData = MutableLiveData<com.alperenbabagil.dataholder.DataHolder<String>>()

    private val singleRetrieveLiveData = MutableLiveData<com.alperenbabagil.dataholder.DataHolder<String>>()

    @MockK
    lateinit var singleInteractor : Interactor.SingleInteractor<Interactor.Params,String>

    @MockK
    lateinit var singleRetrieveInteractor : Interactor.SingleRetrieveInteractor<String>

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    class DummyViewModel() : ViewModel(),CABViewModel{
        override val jobMap: HashMap<String, Job> = hashMapOf()
    }

    @Test
    fun  `single interactor test`(){

        val valueObserver : Observer<com.alperenbabagil.dataholder.DataHolder<String>> = mockk(relaxUnitFun = true)

        coEvery { singleInteractor.execute(param) } coAnswers { com.alperenbabagil.dataholder.DataHolder.Success(singleLiveDataValue) }

        singleLiveData.observeForever(valueObserver)

        val loadingUUID= UUID.randomUUID().toString()

        cabViewModel.execInteractor(liveData = singleLiveData,
            singleInteractor = singleInteractor,
            params = param,
            loadingUUID = loadingUUID
            )

        coVerify { valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Loading().apply { tag=loadingUUID })}

        coVerify {  valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Success(singleLiveDataValue))}

        verifyOrder {
            valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Loading().apply { tag=loadingUUID })
            valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Success(singleLiveDataValue))
        }

        assertEquals(singleLiveDataValue, (singleLiveData.value as com.alperenbabagil.dataholder.DataHolder.Success).data)
    }

    @Test
    fun  `single retrieve interactor test`(){

        val valueObserver : Observer<com.alperenbabagil.dataholder.DataHolder<String>> = mockk(relaxUnitFun = true)

        coEvery { singleRetrieveInteractor.execute() } coAnswers {
            com.alperenbabagil.dataholder.DataHolder.Success(singleRetrieveLiveDataValue)
        }

        singleRetrieveLiveData.observeForever(valueObserver)

        val loadingUUID= UUID.randomUUID().toString()

        cabViewModel.execInteractor(liveData = singleRetrieveLiveData,
            singleRetrieveInteractor = singleRetrieveInteractor,
            loadingUUID = loadingUUID
        )

        coVerify { valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Loading().apply { tag=loadingUUID })}

        coVerify {  valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Success(singleRetrieveLiveDataValue))}

        verifyOrder {
            valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Loading().apply { tag=loadingUUID })
            valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Success(singleRetrieveLiveDataValue))
        }

        assertEquals(singleRetrieveLiveDataValue,
            (singleRetrieveLiveData.value as com.alperenbabagil.dataholder.DataHolder.Success).data)
    }
}