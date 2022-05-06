package com.naveenraj.optisolpro.di

import com.naveenraj.optisolpro.presenter.RetroServiceInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
    @Module
    @InstallIn(SingletonComponent::class)
    object AppModule {

        val LOCAL_BASE_URL = "https://reqres.in/api/"

        @Provides
        @Singleton
        fun getRetroServiceInstance(retrofit: Retrofit): RetroServiceInstance {
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