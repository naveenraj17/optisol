package com.naveenraj.optisolpro.presenter

import com.naveenraj.optisolpro.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
    @Module
    @InstallIn(SingletonComponent::class)
object AppModule {

        val LOCAL_BASE_URL = "https://reqres.in/api/"

        @Provides
        @Singleton
        fun getRetroServiceInstance(retrofit: Retrofit): RetroServiceInstance{
            return retrofit.create(RetroServiceInstance::class.java)
        }
        @Provides
        @Singleton
//        fun provideCryptocurrencyRepository():VideoRepo=VideoRepoImpl()
        fun getRetrofitClient(): Retrofit {
            return Retrofit.Builder().baseUrl(LOCAL_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

}