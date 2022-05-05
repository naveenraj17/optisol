package com.naveenraj.optisolpro.presenter

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.naveenraj.optisolpro.model.VideoReponse
import com.naveenraj.optisolpro.utils.api.APIClient.Companion.getRetrofitClient
import com.naveenraj.optisolpro.utils.api.APInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class VideoRepoImpl @Inject constructor(private val retroInstance: RetroServiceInstance) {

    fun makeApiCall(query: Int?, liveData: MutableLiveData<VideoReponse>){
        val call: Call<VideoReponse> = retroInstance.reqVideoResponse(query)
        call?.enqueue(object : Callback<VideoReponse>{
            override fun onResponse(call: Call<VideoReponse>, response: Response<VideoReponse>) {
            liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<VideoReponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }


     fun getVideoResponse(page:Int): VideoReponse {
        lateinit var  liveData: VideoReponse

        val apiService: APInterface? = getRetrofitClient()?.create(APInterface::class.java)
        apiService?.reqVideoResponse(page)?.enqueue(object :
            Callback<VideoReponse?> {

            override fun onResponse(
                call: Call<VideoReponse?>?,
                response: Response<VideoReponse?>
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData=response.body()!! as VideoReponse
                }else{

                }
            }

            public override fun onFailure(call: Call<VideoReponse?>, t: Throwable?) {
                Log.e("Er", "onFailure" + call.toString())
            }
        })
        return liveData
    }


}