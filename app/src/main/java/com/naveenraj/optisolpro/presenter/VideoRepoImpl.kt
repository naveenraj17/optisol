package com.naveenraj.optisolpro.presenter

import android.util.Log
import com.naveenraj.optisolpro.model.VideoReponse
import com.naveenraj.optisolpro.utils.api.APIClient.Companion.getRetrofitClient
import com.naveenraj.optisolpro.utils.api.APInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoRepoImpl:VideoRepo {



    override fun getVideoResponse(page:Int): ArrayList<VideoReponse> {
        var liveData: ArrayList<VideoReponse> = ArrayList()

        val apiService: APInterface? = getRetrofitClient()?.create(APInterface::class.java)
        apiService?.reqVideoReponse(page)?.enqueue(object :
            Callback<VideoReponse?> {

            override fun onResponse(
                call: Call<VideoReponse?>?,
                response: Response<VideoReponse?>
            ) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData=response.body()!! as ArrayList<VideoReponse>
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