package com.naveenraj.optisolpro.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naveenraj.optisolpro.model.VideoReponse
import com.naveenraj.optisolpro.presenter.VideoRepo
//import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

//@HiltViewModel
class VideoFeedViewModel @Inject constructor(
    private val videoRepo: VideoRepo
) : ViewModel(){

    private val response = MutableLiveData<ArrayList<VideoReponse>>()
    val cryptoCurrency: MutableLiveData<ArrayList<VideoReponse>> = response
    init {
        loadData()
    }

    // getting cryptocurrencies list using
    // repository and passing it into live data
    private fun loadData() {
        response.value = videoRepo.getVideoResponse(1)
    }

}