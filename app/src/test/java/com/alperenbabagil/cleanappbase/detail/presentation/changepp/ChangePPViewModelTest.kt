package com.alperenbabagil.cleanappbase.detail.presentation.changepp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.data.model.ServerError
import com.alperenbabagil.cleanappbase.core.domain.BaseSingleInteractor
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.GetProfileDetailInteractor
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import io.mockk.*
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class ChangePPViewModelTest {

    @MockK
    lateinit var getProfileDetailInteractor : BaseSingleInteractor<GetProfileDetailInteractor.Params, ProfileDetail>

    val userId = "userIdX"

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `Get profile detail test`() {

        val expectedDetail = ProfileDetail(
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

        var requestParam = GetProfileDetailInteractor.Params(userId,RequestResultType.SUCCESS,2000L)

        coEvery { getProfileDetailInteractor.execute(requestParam) } coAnswers { DataHolder.Success(expectedDetail)}

        val changePPViewModel = spyk(ChangePPViewModel(getProfileDetailInteractor))

        val valueObserver : Observer<DataHolder<ProfileDetail>> = mockk(relaxUnitFun = true)

        changePPViewModel.profileDetailLiveData.observeForever(valueObserver)

        runBlocking {
            changePPViewModel.getProfileDetail(userId,RequestResultType.SUCCESS,2000L)
        }

        coVerify {  valueObserver.onChanged(DataHolder.Success(expectedDetail))}

        assertEquals(expectedDetail,(changePPViewModel.profileDetailLiveData.value as DataHolder.Success).data)

        requestParam = GetProfileDetailInteractor.Params(userId,RequestResultType.FAIL,2000L)

        val expectedFail = DataHolder.Fail(error = ServerError("serverErrorStr",7))

        coEvery { getProfileDetailInteractor.execute(requestParam) } coAnswers {expectedFail}

        runBlocking {
            changePPViewModel.getProfileDetail(userId,RequestResultType.FAIL,2000L)
        }

        coVerify {  valueObserver.onChanged(expectedFail)}

        assertEquals(expectedFail.error,(changePPViewModel.profileDetailLiveData.value as DataHolder.Fail).error)


    }
}