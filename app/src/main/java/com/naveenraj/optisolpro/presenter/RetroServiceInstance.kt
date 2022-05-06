package com.naveenraj.optisolpro.presenter

import com.naveenraj.optisolpro.model.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInstance {

    @GET("users")
    fun reqVideoResponse(@Query("page") page: Int?): Call<VideoResponse>



}