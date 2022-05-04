package com.naveenraj.optisolpro.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import com.google.android.material.textfield.TextInputEditText
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.RoomData
import com.naveenraj.optisolpro.utils.FragmentAdapter
import com.naveenraj.optisolpro.utils.RoomAdapter

class DashboardView : AppCompatActivity(),RoomAdapter.ClickListener {
    private lateinit var tabLayout : TabLayout



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        initViews()
        tabLayout.addTab(tabLayout.newTab().setText("Videos"))
        tabLayout.addTab(tabLayout.newTab().setText("Feeds"))
        tabLayout.tabGravity = TabLayout.GRAVITY_FILL
        val adapter = FragmentAdapter(supportFragmentManager, tabLayout!!.tabCount,this@DashboardView)

        val myviewpager : ViewPager = findViewById(R.id.view_pager)
        myviewpager.adapter = adapter
        myviewpager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                myviewpager.currentItem = tab.position

            }
            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
//        tabLayout.post { tabLayout.setupWithViewPager(myviewpager) }
    }

    private fun initViews() {
        tabLayout = findViewById(R.id.tab)
    }

    override fun clickOperation(data: RoomData) {
        lateinit var mBottomSheetDialog: BottomSheetDialog
        mBottomSheetDialog = BottomSheetDialog(this,R.style.AppBottomSheetDialogTheme)
        val sheetView = this.layoutInflater.inflate(R.layout.create_room, null)
        mBottomSheetDialog.setContentView(sheetView)
        val roomName: TextInputEditText = sheetView.findViewById(R.id.room_name)
    }
}