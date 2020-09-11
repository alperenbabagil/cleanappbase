package com.alperenbabagil.cleanappbase.core.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alperenbabagil.cabdata.model.BaseApiResponse
import com.alperenbabagil.cabdomain.model.DataHolder
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants.Companion.SERVER_STATUS_FAIL
import com.alperenbabagil.cleanappbase.core.data.CoreDataConstants.Companion.SERVER_STATUS_SUCCESS
import com.alperenbabagil.cleanappbase.core.data.model.CABDemoBaseApiResponse
import com.alperenbabagil.cleanappbase.core.data.model.ServerError
import com.google.gson.annotations.SerializedName
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.SpyK
import io.mockk.spyk
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class DummyResponseType(@SerializedName("dummy_data") val dummyData:String)

class CABDemoApiCallAdapterTest {

    @get:Rule
    val instantExecutorRole = InstantTaskExecutorRule()

    @SpyK
    var cabDemoApiCallAdapter = CABDemoApiCallAdapter()

    @MockK
    lateinit var response: Response<CABDemoBaseApiResponse<DummyResponseType>>

    private val dummyDataString= "dummyDataString"
    private val serverErrorString= "serverErrorString"
    private val serverErrorCode= 7

    val dummyInstance = DummyResponseType(dummyDataString)

    @Before
    fun setUp() = MockKAnnotations.init(this, relaxUnitFun = true)

    @Test
    fun `CabDemoApiCallAdapter test`() {

        val serviceBody: suspend () -> Response<out BaseApiResponse<DummyResponseType>>? = spyk{
            response
        }

        coEvery { response.code() } coAnswers {200}

        coEvery { response.isSuccessful } coAnswers {true}

        coEvery { response.body() } coAnswers {CABDemoBaseApiResponse(SERVER_STATUS_SUCCESS,dummyInstance,null)}

        coEvery { serviceBody.invoke() } returns response

        var resultDH : DataHolder<DummyResponseType>

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { serviceBody.invoke() }
        }

        // Testing success state
        assertTrue(resultDH is DataHolder.Success)

        // Testing success data
        assertEquals(dummyInstance,(resultDH as DataHolder.Success).data)

        coEvery { response.body() } coAnswers {CABDemoBaseApiResponse(SERVER_STATUS_FAIL,dummyInstance,
            ServerError(serverErrorString,serverErrorCode)
        )}

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { serviceBody.invoke() }
        }

        // Testing fail state
        assertTrue(resultDH is DataHolder.Fail)

        // Testing fail error string
        assertEquals(serverErrorString,((resultDH as DataHolder.Fail).error as ServerError).errorMessage)

        // Testing fail error code
        assertEquals(serverErrorCode,((resultDH as DataHolder.Fail).error as ServerError).errorCode)

        coEvery { response.body() } coAnswers {CABDemoBaseApiResponse("INVALID STATUS",dummyInstance,null)}

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { serviceBody.invoke() }
        }
        // Testing invalid status string fail
        assertTrue(resultDH is DataHolder.Fail)

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { getRetrofit(ResponseStringType.SUCCESS,200).create(DummyService::class.java).getDummy() }
        }
        // Testing json string success
        assertTrue(resultDH is DataHolder.Success)

        // Testing success data
        assertEquals(dummyInstance,(resultDH as DataHolder.Success).data)

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { getRetrofit(ResponseStringType.SUCCESS,500).create(DummyService::class.java).getDummy() }
        }

        // Testing non-success http response code
        assertTrue(resultDH is DataHolder.Fail)

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { getRetrofit(ResponseStringType.SUCCESS,404).create(DummyService::class.java).getDummy() }
        }

        // Testing non-success http response code
        assertTrue(resultDH is DataHolder.Fail)

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { getRetrofit(ResponseStringType.FAIL,200).create(DummyService::class.java).getDummy() }
        }

        // Testing fail state json
        assertTrue(resultDH is DataHolder.Fail)

        // Testing fail state json server error content
        assertEquals(serverErrorString,(resultDH as DataHolder.Fail).errStr)

        // Testing fail state json server error code
        assertEquals(serverErrorCode,((resultDH as DataHolder.Fail).error as ServerError).errorCode)

        runBlocking {
            resultDH = cabDemoApiCallAdapter.adapt { getRetrofit(ResponseStringType.INVALID_JSON,200).create(DummyService::class.java).getDummy() }
        }

        // Testing invalid json
        assertTrue(resultDH is DataHolder.Fail)

    }

    private fun getRetrofit(responseStringType: ResponseStringType,code:Int): Retrofit {
        val client=OkHttpClient.Builder()
            .addInterceptor(MockInterceptor<DummyResponseType>(responseStringType,code))
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(CoreDataConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    interface DummyService{
        @GET("/dummy")
        suspend fun getDummy() : Response<CABDemoBaseApiResponse<DummyResponseType>>
    }

    class MockInterceptor<T>(private val responseStringType: ResponseStringType,private val code:Int) : Interceptor {

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val responseString = when(responseStringType) {
                ResponseStringType.SUCCESS -> "{\"status\":\"success\",\"data\":{\"dummy_data\":\"dummyDataString\"}}"
                ResponseStringType.FAIL -> "{\"status\":\"fail\"," +
                        "\"error\":{\"errorMessage\":\"serverErrorString\",\"errorCode\":7}}"
                ResponseStringType.INVALID_JSON -> "{\"status\":\"fail\"\"data\":\"\",\"error\"\"serverErrorString\"}"
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(code)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        }
    }

    enum class ResponseStringType{
        SUCCESS,FAIL,INVALID_JSON
    }
}