package com.naveenraj.optisolpro.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.naveenraj.optisolpro.model.VideoResponse
import com.naveenraj.optisolpro.utils.api.APIClient.Companion.getRetrofitClient
import com.naveenraj.optisolpro.utils.api.APInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(private val retroInstance: RetroServiceInstance) {

    fun makeApiCall(query: Int?, liveData: MutableLiveData<VideoResponse>){
        val call: Call<VideoResponse> = retroInstance.reqVideoResponse(query)
        call?.enqueue(object : Callback<VideoResponse>{
            override fun onResponse(call: Call<VideoResponse>, response: Response<VideoResponse>) {
            liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<VideoResponse>, t: Throwable) {
            }

        })
    }


     fun getVideoResponse(page:Int): VideoResponse {
        lateinit var  liveData: VideoResponse

        val apiService: APInterface? = getRetrofitClient()?.create(APInterface::class.java)
        apiService?.reqVideoResponse(page)?.enqueue(object :
            Callback<VideoResponse?> {

            override fun onResponse(
                call: Call<VideoResponse?>?,
                response: Response<VideoResponse?>
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData=response.body()!! as VideoResponse
                }else{

                }
            }

            public override fun onFailure(call: Call<VideoResponse?>, t: Throwable?) {
                Log.e("Er", "onFailure" + call.toString())
            }
        })
        return liveData
    }


}