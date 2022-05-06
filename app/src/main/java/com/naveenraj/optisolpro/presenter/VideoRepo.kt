package com.naveenraj.optisolpro.presenter

import com.naveenraj.optisolpro.model.VideoResponse

interface VideoRepo {
    fun getVideoResponse(page:Int): ArrayList<VideoResponse>
}
