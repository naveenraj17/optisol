package com.naveenraj.optisolpro.presenter

import com.naveenraj.optisolpro.model.VideoReponse

interface VideoRepo {
    fun getVideoResponse(page:Int): ArrayList<VideoReponse>
}
