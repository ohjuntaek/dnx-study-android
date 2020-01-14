package kr.dnx.ble.android.mysunflower

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

object OkHttpRetrofitManager{
    private val API_URL: String = "https://jsonplaceholder.typicode.com/"
    private val READ_TIMEOUT: Long = 15
    private val WRITE_TIMEOUT: Long = 15
    private val CONNECT_TIMEOUT: Long = 15
    private var okHttpClient: OkHttpClient
    private var mySunflowerApi: MySunflowerApi
    private var retrofitClient: Retrofit

    init {
        val httpLoggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        okHttpClient = OkHttpClient().newBuilder().apply {
            addInterceptor(httpLoggingInterceptor)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        }.build()

        retrofitClient = Retrofit.Builder().apply {
            baseUrl(API_URL)
            client(okHttpClient)
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            addConverterFactory(GsonConverterFactory.create())
        }.build()

        mySunflowerApi = retrofitClient.create()
    }

    fun getInstance(): MySunflowerApi {
        return mySunflowerApi
    }
}