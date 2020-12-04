package com.alperenbabagil.cleanappbase.detail.presentation.happiness

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.cleanappbase.core.data.model.ServerError
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.BaseSingleFlowInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.detail.domain.happiness.HappinessInteractor
import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.GetProfileDetailInteractor
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.cleanappbase.detail.presentation.profiledetail.ProfileDetailViewModel
import com.alperenbabagil.dataholder.DataHolder
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


class HappinessViewModelTest{

    @MockK
    lateinit var happinessInteractor : BaseSingleFlowInteractor<HappinessInteractor.Params,
            HappinessResult>

    val userId = "userIdX"

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `Get happiness test`(){

        val expectedHappinessResult= HappinessResult(99)

        val requestProfileDetail =ProfileDetail(
            userId,
            "titleT",
            "https://robohash.org/userIdX",
            "skillT",
            Date().apply { time = 973807200000 },
            "colorT",
            "martialT",
            "langT",
            "companyT"
        )

        var requestParam = HappinessInteractor.Params(
            requestProfileDetail
        )

        coEvery { happinessInteractor.execute(requestParam) } coAnswers {
            flow{
                emit(DataHolder.Success(expectedHappinessResult))
            }
        }

        val happinessViewModel = spyk(HappinessViewModel(happinessInteractor))

        val valueObserver : Observer<DataHolder<HappinessResult>> = mockk(relaxUnitFun = true)

        var resultLiveData : LiveData<DataHolder<HappinessResult>>
        runBlocking {
            resultLiveData=happinessViewModel.getHappiness(requestProfileDetail).liveData
            resultLiveData.observeForever(valueObserver)
        }

        coVerify {  valueObserver.onChanged(DataHolder.Success(expectedHappinessResult))}

        Assert.assertEquals(
            expectedHappinessResult,
            (resultLiveData.value as DataHolder.Success).data
        )

    }
}