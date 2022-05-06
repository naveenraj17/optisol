package com.naveenraj.optisolpro.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class VideoData(first_name:String, last_name:String, email:String, avatar:String) {
    @SerializedName("id")
    @Expose
     var id: Int? = null

    @SerializedName("email")
    @Expose
     var email: String? = null

    @SerializedName("first_name")
    @Expose
     var firstName: String? = null

    @SerializedName("last_name")
    @Expose
     var lastName: String? = null

    @SerializedName("avatar")
    @Expose
     var avatar: String? = null
    
    init {
        firstName = first_name
        lastName = last_name
        this.email = email
        this.avatar = avatar
    }
}
