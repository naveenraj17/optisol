package com.naveenraj.optisolpro.utils.api

import android.app.Application

import com.naveenraj.optisolpro.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class APIClient : Application() {
    var mInstance: APIClient? = null

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object{
        fun getRetrofitClient(): Retrofit? {
            var retrofit: Retrofit? = null
            val LOCAL_BASE_URL = "https://reqres.in/api/"

            if (retrofit == null) {
                var client: OkHttpClient = OkHttpClient.Builder().connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES).build()
                if (BuildConfig.DEBUG) {
                    val interceptor = HttpLoggingInterceptor()
                    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client = OkHttpClient.Builder()
                        .addInterceptor(interceptor).build()
                }
                retrofit = Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(LOCAL_BASE_URL)
                    .build()
            }
            return retrofit
        }
    }

//    @Synchronized
//    fun getInstance(): APIClient? {
//        return mInstance
//    }
}