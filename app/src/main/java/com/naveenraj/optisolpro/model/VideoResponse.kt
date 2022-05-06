package com.naveenraj.optisolpro.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class VideoResponse {
    @SerializedName("page")
    @Expose
     val page: Int? = null

    @SerializedName("per_page")
    @Expose
     val perPage: Int? = null

    @SerializedName("total")
    @Expose
     val total: Int? = null

    @SerializedName("total_pages")
    @Expose
     val totalPages: Int? = null

    @SerializedName("data")
    @Expose
     val data: List<VideoData>? = null

    @SerializedName("support")
    @Expose
     val support: Support? = null
}