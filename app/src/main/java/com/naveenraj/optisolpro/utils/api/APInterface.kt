package com.naveenraj.optisolpro.utils.api

import com.naveenraj.optisolpro.model.VideoReponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APInterface {


    @GET("users")
    fun reqVideoReponse(@Query("page") PatientId: Int?): Call<VideoReponse?>?

}