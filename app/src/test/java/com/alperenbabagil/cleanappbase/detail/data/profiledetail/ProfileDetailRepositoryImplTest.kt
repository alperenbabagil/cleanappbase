package com.alperenbabagil.cleanappbase.detail.data.profiledetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.core.data.model.Error
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.core.data.model.ServerError
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailDataTemplate
import com.alperenbabagil.cleanappbase.detail.data.profiledetail.model.ProfileDetailNetworkDTO
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


class ProfileDetailRepositoryImplTest {

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    private val userName = "userIdX"
    private val serverErrorStr = "Server business error"

    @MockK
    lateinit var dataSource: BaseDataSource<ResponseTemplate<ProfileDetailDataTemplate>, ProfileDetailNetworkDTO>

    @Test
    fun `get Profile Detail Test`() {

        val request =
            ResponseTemplate(data = ProfileDetailDataTemplate(userName = userName))

        val profileDetailNetworkDTO = ProfileDetailNetworkDTO(
            userName,
            "titleT",
            "skillT",
            "2000-11-10",
            "colorT",
            "martialT",
            "langT",
            "companyT"
        )

        val expectedDetail = ProfileDetail(
            userName,
            "titleT",
            "https://robohash.org/userIdX",
            "skillT",
            Date().apply { time = 973807200000 },
            "colorT",
            "martialT",
            "langT",
            "companyT"
        )

        coEvery { dataSource.getResult(request) } coAnswers {
            DataHolder.Success(profileDetailNetworkDTO)
        }

        val profileDetailRepositoryImpl = spyk(ProfileDetailRepositoryImpl(dataSource))

        var resultDH: DataHolder<ProfileDetail>

        runBlocking {
            resultDH =
                profileDetailRepositoryImpl.getProfileDetail(userName, RequestResultType.SUCCESS)
        }

        assertTrue(resultDH is DataHolder.Success)

        assertEquals(expectedDetail, (resultDH as DataHolder.Success).data)

        val failRequest = ResponseTemplate<ProfileDetailDataTemplate>().apply {
            status= CoreDataConstants.SERVER_STATUS_FAIL
            data=null
            error= Error(
                7,
                "Server business error"
            )
        }

        val expectedFail = DataHolder.Fail(error = ServerError(serverErrorStr,7))

        coEvery { dataSource.getResult(failRequest) } coAnswers {
            DataHolder.Fail(error = ServerError(serverErrorStr,7))
        }

        runBlocking {
            resultDH =
                profileDetailRepositoryImpl.getProfileDetail(userName, RequestResultType.FAIL)
        }

        assertTrue(resultDH is DataHolder.Fail)

        assertEquals(expectedFail.error, (resultDH as DataHolder.Fail).error)

    }
}