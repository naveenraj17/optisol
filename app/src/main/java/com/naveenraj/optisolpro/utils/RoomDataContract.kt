package com.naveenraj.optisolpro.utils

import android.provider.BaseColumns

object RoomDataContract {
    class RoomData : BaseColumns {
        companion object {
             val TABLE_NAME = "RoomData"
             val ID = "id"
             val NAME = "roomName"
             val ISLIVE = "isLive"
             val DATETIME = "dateTime"
        }
    }

}