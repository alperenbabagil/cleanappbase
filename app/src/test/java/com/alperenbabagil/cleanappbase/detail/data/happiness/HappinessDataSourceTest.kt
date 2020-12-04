package com.alperenbabagil.cleanappbase.detail.data.happiness

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alperenbabagil.cleanappbase.detail.domain.happiness.model.HappinessResult
import com.alperenbabagil.cleanappbase.detail.domain.profiledetail.model.ProfileDetail
import com.alperenbabagil.dataholder.DataHolder
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
import java.util.*

class HappinessDataSourceTest{
    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @MockK
    lateinit var happinessProducer: HappinessProducer

    @Before
    fun setUp() = MockKAnnotations.init(this)

    @ExperimentalCoroutinesApi
    @Test
    fun `get happiness test`(){
        val requestProfileDetail = ProfileDetail(
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

        coEvery { happinessProducer.produce(requestProfileDetail.username!!) } coAnswers {99}

        val happinessGenerator =HappinessGenerator(happinessProducer)

        val happinessDataSource = spyk(HappinessDataSource(happinessGenerator))

        var resultDH : DataHolder<HappinessResult>?=null

        val expectedHappinessResult= HappinessResult(99)

        try {
            runBlocking {
                happinessDataSource.getDataSourceResult(requestProfileDetail).collect {
                    resultDH=it
                    this.cancel()
                }
            }
        }
        catch (e:Exception){}

        assertTrue(resultDH is DataHolder.Success)
        assertEquals(expectedHappinessResult, (resultDH as DataHolder.Success).data)

    }

}