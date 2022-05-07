package com.naveenraj.optisolpro.utils.network

import androidx.lifecycle.MutableLiveData
import com.naveenraj.optisolpro.model.VideoResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(private val retroInstance: RetroServiceInstance) {

    fun makeApiCall(query: Int?, liveData: MutableLiveData<VideoResponse>){
        val call: Call<VideoResponse> = retroInstance.reqVideoResponse(query)
        call.enqueue(object : Callback<VideoResponse>{
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
            }

        })
    }
}