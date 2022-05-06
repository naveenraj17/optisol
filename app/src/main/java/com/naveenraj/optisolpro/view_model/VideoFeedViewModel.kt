package com.naveenraj.optisolpro.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.naveenraj.optisolpro.model.VideoResponse
import com.naveenraj.optisolpro.presenter.VideoRepoImpl
import dagger.hilt.android.lifecycle.HiltViewModel
//import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoFeedViewModel @Inject constructor(private val repository: VideoRepoImpl
) : ViewModel(){
    lateinit var liveDataList: MutableLiveData<VideoResponse>

    init{
        liveDataList=MutableLiveData()

    }

    fun getLiveDataObserver(): MutableLiveData<VideoResponse>{
        return liveDataList
    }


    fun loadListofData(page: Int) {
        repository.makeApiCall(page,liveDataList)
    }

}