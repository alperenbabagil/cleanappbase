package com.alperenbabagil.cleanappbase.detail.data.happiness

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alperenbabagil.cleanappbase.core.data.BaseFlowDataSource
import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.dataholder.DataHolder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import junit.framework.Assert
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


class HappinessRepositoryImplTest{

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @MockK
    lateinit var dataSource: BaseFlowDataSource<ProfileDetail, HappinessResult>

    @Test
    fun `get happiness test`(){

        val requestProfileDetail =ProfileDetail(
            "userName",
            "titleT",
            "https://robohash.org/userIdX",
            "skillT",
            Date().apply { time = 973807200000 },
            "colorT",
            "martialT",
            "langT",
            "companyT"
        )

        val expectedHappinessResult= HappinessResult(99)

        coEvery { dataSource.getDataSourceResult(requestProfileDetail) } coAnswers {
            flow{
                emit(DataHolder.Success(expectedHappinessResult))
            }
        }

        val happinessRepositoryImpl = spyk(HappinessRepositoryImpl(dataSource))

        var resultDH: DataHolder<HappinessResult>?=null

        runBlocking {
                happinessRepositoryImpl.getHappiness(requestProfileDetail).collect {
                    resultDH=it
                }
        }

        assertTrue(resultDH is DataHolder.Success)

        assertEquals(expectedHappinessResult, (resultDH as DataHolder.Success).data)


    }
}