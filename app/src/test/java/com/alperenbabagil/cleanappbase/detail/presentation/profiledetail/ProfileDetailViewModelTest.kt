package com.alperenbabagil.cleanappbase.detail.presentation.profiledetail

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
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class ProfileDetailViewModelTest{

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

        var requestParam = GetProfileDetailInteractor.Params(userId, RequestResultType.SUCCESS,2000L)

        coEvery { getProfileDetailInteractor.execute(requestParam) } coAnswers { com.alperenbabagil.dataholder.DataHolder.Success(expectedDetail)}

        val profileDetailViewModel = spyk(ProfileDetailViewModel(getProfileDetailInteractor))

        val valueObserver : Observer<com.alperenbabagil.dataholder.DataHolder<ProfileDetail>> = mockk(relaxUnitFun = true)

        profileDetailViewModel.profileDetailLiveData.observeForever(valueObserver)

        runBlocking {
            profileDetailViewModel.getProfileDetail(userId, RequestResultType.SUCCESS,2000L)
        }

        coVerify {  valueObserver.onChanged(com.alperenbabagil.dataholder.DataHolder.Success(expectedDetail))}

        Assert.assertEquals(
            expectedDetail,
            (profileDetailViewModel.profileDetailLiveData.value as com.alperenbabagil.dataholder.DataHolder.Success).data
        )

        requestParam = GetProfileDetailInteractor.Params(userId, RequestResultType.FAIL,2000L)

        val expectedFail = com.alperenbabagil.dataholder.DataHolder.Fail(error = ServerError("serverErrorStr",7))

        coEvery { getProfileDetailInteractor.execute(requestParam) } coAnswers {expectedFail}

        runBlocking {
            profileDetailViewModel.getProfileDetail(userId, RequestResultType.FAIL,2000L)
        }

        coVerify {  valueObserver.onChanged(expectedFail)}

        Assert.assertEquals(
            expectedFail.error,
            (profileDetailViewModel.profileDetailLiveData.value as com.alperenbabagil.dataholder.DataHolder.Fail).error
        )


    }
}