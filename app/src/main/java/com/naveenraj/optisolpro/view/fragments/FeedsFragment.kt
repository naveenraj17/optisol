package com.naveenraj.optisolpro.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.RoomData
import com.naveenraj.optisolpro.utils.adapter.RoomAdapter
import com.naveenraj.optisolpro.utils.SQLiteManager
import com.naveenraj.optisolpro.view.DashboardView
import java.text.SimpleDateFormat
import java.util.*


class FeedsFragment(click: RoomAdapter.ClickListener) : Fragment() {

    private lateinit var mBottomSheetDialog: BottomSheetDialog
    private lateinit var addRoom: FloatingActionButton
    private lateinit var note: TextView
    private lateinit var roomRecycler: RecyclerView
    private lateinit var sqLiteManager: SQLiteManager
    private val dateFormat:String = "yyyy-MM-dd HH:mm:ss"
    private var data:ArrayList<RoomData>? = ArrayList()
    private val listener = click

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        sqLiteManager = SQLiteManager(requireContext())

        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        initViews(view)

        try {
             data = sqLiteManager.getRecords()
            if(data?.size!!>0){
                filterData(data!!)
                val adapter =  RoomAdapter(filterData(data!!),listener)
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
        val createL:LinearLayout = sheetView.findViewById(R.id.createL)
        val updateL:LinearLayout = sheetView.findViewById(R.id.updateL)
        updateL.visibility = View.GONE
        createL.visibility = View.VISIBLE
        cancel.setOnClickListener{
            mBottomSheetDialog.dismiss()
            isLive.isChecked = false
            roomName.text?.clear()
            roomName.clearFocus()
        }

        create.setOnClickListener{
            val text = roomName.text.toString()
            var check = true
            for(i in this.data!!){
                if(text.equals(i.name, ignoreCase = true)){
                    check = false
                    break
                }
            }

            if(check){
                if(text.isNotEmpty()){
                    val sdf = SimpleDateFormat(dateFormat)
                    sdf.timeZone = TimeZone.getTimeZone("UTC")
                    val utcTime: String = sdf.format(Date())
                    val data = RoomData("",text,isLive.isChecked,utcTime)
                    if(sqLiteManager.addRoomDetails(data)){
                        currentPage()
                    }
                }else{
                    Toast.makeText(requireContext(),"Room Name should be Unique", Toast.LENGTH_SHORT).show()
                }
                }else{
                roomName.error = "Room Name"
            }

        }
        addRoom.setOnClickListener {
            mBottomSheetDialog.show()
        }

        return view
    }

    private fun initViews(view: View) {
        note = view.findViewById(R.id.note)
        addRoom = view.findViewById(R.id.add_room)
        roomRecycler = view.findViewById(R.id.room_recycler)
    }

    private fun filterData(data: ArrayList<RoomData>):ArrayList<RoomData> {
        val listData : ArrayList<RoomData> = ArrayList()
        for(i in data){
            if(i.isLive){
                listData.add(i)
            }
        }
        for(i in data){
            if(!i.isLive){
                listData.add(i)
            }
        }
        return listData
    }


    private fun currentPage() {
        startActivity(Intent(requireContext(),DashboardView::class.java)
            .putExtra("item","1"))
    }

}