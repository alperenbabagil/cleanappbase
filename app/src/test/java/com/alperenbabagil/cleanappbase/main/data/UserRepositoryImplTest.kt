package com.alperenbabagil.cleanappbase.main.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.data.BaseDataSource
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants
import com.alperenbabagil.cleanappbase.core.data.model.Error
import com.alperenbabagil.cleanappbase.core.data.model.ResponseTemplate
import com.alperenbabagil.cleanappbase.core.data.model.ServerError
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.main.data.model.UserListDataTemplate
import com.alperenbabagil.cleanappbase.main.data.model.UserListItemNetworkDTO
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

internal class UserRepositoryImplTest {


    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    private val userName = "userIdX"
    private val serverErrorStr = "Server business error"

    @MockK
    lateinit var dataSource: BaseDataSource<ResponseTemplate<UserListDataTemplate>,
            List<UserListItemNetworkDTO>>


    @Test
    fun `get Profile Detail Test`() {

        val requestList = mutableListOf<UserListItemNetworkDTO>().apply {
            repeat(10){
                add(UserListItemNetworkDTO("nickName$it","title$it","skill$it","2000-11-10"))
            }
        }

        val expectedList = mutableListOf<UserListItem>().apply {
            repeat(10){
                add(UserListItem("nickName$it","title$it","https://robohash.org/nickName$it","skill$it",
                    Date().apply { time = 973807200000 }))
            }
        }

        coEvery { dataSource.getResult(any()) } coAnswers {
            DataHolder.Success(requestList)
        }

        val userRepository = spyk(UserRepositoryImpl(dataSource))

        var resultDH: DataHolder<List<UserListItem>>

        runBlocking {
            resultDH =
                userRepository.getUsers(RequestResultType.SUCCESS)
        }

        assertTrue(resultDH is DataHolder.Success)

        assertEquals(expectedList, (resultDH as DataHolder.Success).data)

        val failRequest = ResponseTemplate<UserListDataTemplate>().apply {
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
                userRepository.getUsers(RequestResultType.FAIL)
        }

        assertTrue(resultDH is DataHolder.Fail)

        assertEquals(expectedFail.error, (resultDH as DataHolder.Fail).error)
    }
}