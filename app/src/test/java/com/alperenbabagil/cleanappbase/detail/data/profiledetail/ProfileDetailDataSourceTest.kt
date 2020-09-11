package com.alperenbabagil.cleanappbase.detail.data.profiledetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.CABDemoApiCallAdapter
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.core.data.model.CABDemoBaseApiResponse
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.detail.data.ProfileService
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailDataTemplate
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailNetworkDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response


class ProfileDetailDataSourceTest {

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @MockK
    lateinit var profileService: ProfileService
    @MockK
    lateinit var  apiCallAdapter: ApiCallAdapter
    @MockK
    lateinit var response: Response<CABDemoBaseApiResponse<ProfileDetailNetworkDTO>>

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get data test`() {
        val request = BaseRequest(
            ResponseTemplate(data= ProfileDetailDataTemplate(userName = "")))

        val profileDetailNetworkDTO = ProfileDetailNetworkDTO("nameT",
            "titleT",
            "skillT",
            "2000-11-10",
            "colorT",
            "martialT",
            "langT",
            "companyT"
            )

        apiCallAdapter= spyk(CABDemoApiCallAdapter())

        coEvery { apiCallAdapter.adapt{ response } } coAnswers {
            DataHolder.Success(profileDetailNetworkDTO)
        }

        val profileDetailDataSource = spyk(ProfileDetailDataSource(profileService,apiCallAdapter))

        coEvery {
            profileService.getProfileDetail(request)} coAnswers {
            response
        }

        coEvery { response.code() } coAnswers {200}

        coEvery { response.isSuccessful } coAnswers {true}

        coEvery { response.body() } coAnswers {CABDemoBaseApiResponse(CoreDataConstants.SERVER_STATUS_SUCCESS,profileDetailNetworkDTO,null)}


        var resultDH: DataHolder<ProfileDetailNetworkDTO>

        runBlocking {
            resultDH=profileDetailDataSource.getDataSourceResult(request)
        }

        assertTrue(resultDH is DataHolder.Success)


    }
}