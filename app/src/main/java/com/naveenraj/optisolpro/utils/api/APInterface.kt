package com.naveenraj.optisolpro.utils.api

import com.naveenraj.optisolpro.model.VideoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APInterface {


    @GET("users")
    fun reqVideoResponse(@Query("page") PatientId: Int?): Call<VideoResponse?>?

}