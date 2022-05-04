package com.naveenraj.optisolpro.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.naveenraj.optisolpro.view.fragments.FeedsFragment
import com.naveenraj.optisolpro.view.fragments.VideoFragment

class FragmentAdapter (fm: FragmentManager, var totalTabs: Int,listener: RoomAdapter.ClickListener) :
    FragmentPagerAdapter(fm) {
    var listener = listener
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> VideoFragment()

            1 -> FeedsFragment(listener)

            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}