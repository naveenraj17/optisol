package com.naveenraj.optisolpro.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.RoomData
import com.naveenraj.optisolpro.utils.RoomAdapter
import com.naveenraj.optisolpro.utils.SQLiteManager
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FeedsFragment(click:RoomAdapter.ClickListener) : Fragment() {

    private lateinit var mBottomSheetDialog: BottomSheetDialog
    private lateinit var addRoom: FloatingActionButton
    private lateinit var note: TextView
    private lateinit var roomRecycler: RecyclerView
    private lateinit var sqLiteManager: SQLiteManager
    private val DATEFORMAT:String = "yyyy-MM-dd HH:mm:ss"
    private val listener = click

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        sqLiteManager = SQLiteManager(requireContext())
        note = view.findViewById(R.id.note)
        addRoom = view.findViewById(R.id.add_room)
        roomRecycler = view.findViewById(R.id.room_recycler)
        try {
            val data:ArrayList<RoomData>? = sqLiteManager.getRecords()
            if(data?.size!!>0){
                var adapter =  RoomAdapter(data,requireContext(),listener)
                roomRecycler.setHasFixedSize(true)
                roomRecycler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false)
                try{
                    roomRecycler.adapter = adapter
                }catch (e:Exception){}
            }else note.visibility = View.VISIBLE

        } catch (e: Exception) {
            note.visibility = View.VISIBLE
        }






        mBottomSheetDialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme)
        val sheetView = this.layoutInflater.inflate(R.layout.create_room, null)
        mBottomSheetDialog.setContentView(sheetView)
        val roomName:TextInputEditText = sheetView.findViewById(R.id.room_name)
        val isLive:CheckBox = sheetView.findViewById(R.id.is_live)
        val create:Button = sheetView.findViewById(R.id.create)
        val cancel:Button = sheetView.findViewById(R.id.cancel)
        cancel.setOnClickListener{
            mBottomSheetDialog.dismiss()
            isLive.isChecked = false
            roomName.text?.clear()
            roomName.clearFocus()
        }
        create.setOnClickListener{
            val sdf = SimpleDateFormat(DATEFORMAT)
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
            val utcTime: String = sdf.format(Date())
            val data = RoomData("",roomName.text.toString(),isLive.isChecked,utcTime)
            if(sqLiteManager.addRoomDetails(data)){
                mBottomSheetDialog.dismiss()
            }
        }

        addRoom.setOnClickListener {
            mBottomSheetDialog.show()
        }

        return view
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

    }


}