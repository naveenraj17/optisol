package com.naveenraj.optisolpro.di

import com.naveenraj.optisolpro.utils.network.RetroServiceInstance
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

        private const val baseUrl = "https://reqres.in/api/"

        @Provides
        @Singleton
        fun getRetroServiceInstance(retrofit: Retrofit): RetroServiceInstance {
            return retrofit.create(RetroServiceInstance::class.java)
        }
        @Provides
        @Singleton
        fun getRetrofitClient(): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

}