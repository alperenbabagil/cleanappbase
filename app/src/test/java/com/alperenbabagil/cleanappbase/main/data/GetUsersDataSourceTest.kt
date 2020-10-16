package com.alperenbabagil.cleanappbase.main.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alperenbabagil.cabdata.ApiCallAdapter
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.data.CABDemoApiCallAdapter
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.core.data.model.BaseRequest
import com.alperenbabagil.cleanappbase.core.data.model.CABDemoBaseApiResponse
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.core.data.model.ServerError
import com.alperenbabagil.cleanappbase.main.data.model.UserListDataTemplate
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response


class GetUsersDataSourceTest {

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @MockK
    lateinit var userService: UserService

    @MockK
    lateinit var  apiCallAdapter: ApiCallAdapter

    @MockK
    lateinit var response: Response<CABDemoBaseApiResponse<List<UserListItemNetworkDTO>>>

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @Test
    fun `get data test`() {
        val request = BaseRequest(
            ResponseTemplate(data= UserListDataTemplate())
        )

        val expectedList = mutableListOf<UserListItemNetworkDTO>().apply {
            repeat(10){
                add(UserListItemNetworkDTO("nickName$it","title$it","skill$it","2000-11-10"))
            }
        }

        coEvery { apiCallAdapter.adapt<List<UserListItemNetworkDTO>>(any())  } coAnswers {
            DataHolder.Success(expectedList)
        }

        var getUsersDataSource = spyk(GetUsersDataSource(userService,apiCallAdapter))

        var resultDH: DataHolder<List<UserListItemNetworkDTO>>

        runBlocking {
            resultDH=getUsersDataSource.getDataSourceResult(request)
        }

        // Testing against adapter
        Assert.assertTrue(resultDH is DataHolder.Success)
        Assert.assertEquals(expectedList, (resultDH as DataHolder.Success).data)

        val expectedError = DataHolder.Fail(errStr = "expectedErr",error = ServerError("servErr",7))

        coEvery { apiCallAdapter.adapt<List<UserListItemNetworkDTO>>(any())  } coAnswers {
            expectedError
        }

        runBlocking {
            resultDH=getUsersDataSource.getDataSourceResult(request)
        }

        // Testing against adapter
        Assert.assertTrue(resultDH is DataHolder.Fail)
        Assert.assertEquals(expectedError.errStr, (resultDH as DataHolder.Fail).errStr)
        Assert.assertEquals(expectedError.error, (resultDH as DataHolder.Fail).error)


        val adapterInstance = spyk(CABDemoApiCallAdapter())

        coEvery {
            userService.getUsers(any())} coAnswers {
            response
        }

        coEvery { response.code() } coAnswers {200}

        coEvery { response.isSuccessful } coAnswers {true}

        coEvery { response.body() } coAnswers {
            CABDemoBaseApiResponse(CoreDataConstants.SERVER_STATUS_SUCCESS,
                expectedList,null)}

        getUsersDataSource = spyk(GetUsersDataSource(userService,adapterInstance))

        runBlocking {
            resultDH=getUsersDataSource.getDataSourceResult(request)
        }

        Assert.assertEquals(expectedList, (resultDH as DataHolder.Success).data)

        val expectedServerError = ServerError("profileDetailError",7)
        coEvery { response.body() } coAnswers {
            CABDemoBaseApiResponse(CoreDataConstants.SERVER_STATUS_FAIL,
                expectedList, ServerError("profileDetailError",7)
            )}

        runBlocking {
            resultDH=getUsersDataSource.getDataSourceResult(request)
        }

        Assert.assertTrue(resultDH is DataHolder.Fail)
        Assert.assertEquals(expectedServerError, (resultDH as DataHolder.Fail).error)

    }
}