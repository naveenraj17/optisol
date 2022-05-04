package com.naveenraj.optisolpro.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.naveenraj.optisolpro.model.RoomData
import com.naveenraj.optisolpro.utils.RoomDataContract.RoomData.Companion.DATETIME
import com.naveenraj.optisolpro.utils.RoomDataContract.RoomData.Companion.ID
import com.naveenraj.optisolpro.utils.RoomDataContract.RoomData.Companion.ISLIVE
import com.naveenraj.optisolpro.utils.RoomDataContract.RoomData.Companion.NAME
import com.naveenraj.optisolpro.utils.RoomDataContract.RoomData.Companion.TABLE_NAME


class SQLiteManager(context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DATABASE_VERSION) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_ROOM_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db!!.execSQL(DELETE_ROOM_TABLE)

        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun addRoomDetails(data:RoomData): Boolean {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(NAME, data.name)
        values.put(ISLIVE, data.isLive)
        values.put(DATETIME, data.createDataTime)

        val _success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun updateMatchDetails(data:RoomData): Boolean {

//        var id = getRecords()?.get(getRecordCount(TABLE_NAME)-1)?.id
        var id = 2

        val db = this.writableDatabase
        val values = ContentValues()
        if(!data.isLive.equals(""))
            values.put(ISLIVE, data.isLive)
        val _success = db.update(TABLE_NAME,values,"id = ?", arrayOf(id.toString()))
        db.close()
        return (Integer.parseInt("$_success") != -1)
    }

    fun getRecords(): ArrayList<RoomData>? {
        val data = ArrayList<RoomData>()
        val selectQuery = "SELECT * FROM " + TABLE_NAME
        val sqLiteDatabase = this.readableDatabase
        val cursor = sqLiteDatabase.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                var  bean = RoomData(cursor.getString(0),cursor.getString(1),
                    cursor.getInt(2) > 0,cursor.getString(3))
                data.add(bean!!)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return data
    }


    companion object{

        val DB_NAME = "VRooms.db"
        val DATABASE_VERSION = 1

        val CREATE_ROOM_TABLE =
            "CREATE TABLE " + TABLE_NAME + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                    NAME + " TEXT, " +
                    ISLIVE + " BOOLEAN, " +
                    DATETIME + " TEXT " + ")"


        private val DELETE_ROOM_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME

    }
}