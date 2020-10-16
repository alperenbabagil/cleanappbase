package com.alperenbabagil.cleanappbase.main.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alperenbabagil.cabdomain.Interactor
import com.alperenbabagil.dataholder.DataHolder
import com.alperenbabagil.cleanappbase.core.data.model.ServerError
import com.alperenbabagil.cleanappbase.core.domain.model.RequestResultType
import com.alperenbabagil.cleanappbase.main.domain.GetUsersInteractor
import com.alperenbabagil.cleanappbase.main.domain.model.UserListItem
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class MainViewModelTest{

    @MockK
    lateinit var getUsersInteractor:
            Interactor.SingleInteractor<GetUsersInteractor.Params,List<UserListItem>>

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)


    @Test
    fun `Get profile detail test`(){

        val expectedList = mutableListOf<UserListItem>().apply {
            repeat(10){
                add(UserListItem("nickName$it","title$it","https://robohash.org/nickName$it","skill$it",
                    Date().apply { time = 973807200000 }))
            }
        }

        var requestParam = GetUsersInteractor.Params(RequestResultType.SUCCESS)

        coEvery { getUsersInteractor.execute(requestParam) } coAnswers {
            DataHolder.Success(expectedList)}

        val mainViewModel = spyk(MainViewModel(getUsersInteractor))

        val valueObserver : Observer<DataHolder<List<UserListItem>>> = mockk(relaxUnitFun = true)

        mainViewModel.usersLiveData.observeForever(valueObserver)

        runBlocking {
            mainViewModel.getUsers(RequestResultType.SUCCESS)
        }

        coVerify {  valueObserver.onChanged(DataHolder.Success(expectedList))}

        assertEquals(
            expectedList,
            (mainViewModel.usersLiveData.value as DataHolder.Success).data
        )

        requestParam = GetUsersInteractor.Params(RequestResultType.FAIL)

        val expectedFail = DataHolder.Fail(error = ServerError("serverErrorStr",7))

        coEvery { getUsersInteractor.execute(requestParam) } coAnswers {expectedFail}

        runBlocking {
            mainViewModel.getUsers(RequestResultType.FAIL)
        }

        coVerify {  valueObserver.onChanged(expectedFail)}

        assertEquals(
            expectedFail.error,
            (mainViewModel.usersLiveData.value as DataHolder.Fail).error
        )
    }
}