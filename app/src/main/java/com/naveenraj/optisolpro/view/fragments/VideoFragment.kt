package com.naveenraj.optisolpro.view.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.NestedScrollView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.shimmer.ShimmerFrameLayout
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.VideoData
import com.naveenraj.optisolpro.utils.adapter.VideoAdapter
import com.naveenraj.optisolpro.view.DashboardView
import com.naveenraj.optisolpro.view_model.VideoFeedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment() {

    private lateinit var videoRecyclerview: RecyclerView
    private lateinit var adapter: VideoAdapter
    private lateinit var scrollView: NestedScrollView
    private lateinit var shimmer: ShimmerFrameLayout
    private lateinit var shimmerBottom: ShimmerFrameLayout
    private var entireData: ArrayList<VideoData> =ArrayList()
    private var page = 1
    private var limit = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_video, container, false)
        initViews(view)

        shimmer.startShimmerAnimation()
        shimmerBottom.startShimmerAnimation()

        shimmer.visibility =View.VISIBLE
        videoRecyclerview.setHasFixedSize(true)
        videoRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
            initViewModel()

        adapter = VideoAdapter()
        videoRecyclerview.adapter = adapter
        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->

            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                initViewModel()
                shimmerBottom.visibility =View.GONE
                shimmerBottom.stopShimmerAnimation()
            }
        })
        return view
    }

    private fun initViews(view: View) {
        videoRecyclerview = view.findViewById(R.id.video_recycler)
        shimmer = view.findViewById(R.id.shimmer_view_container)
        shimmerBottom = view.findViewById(R.id.shimmer_view)
        scrollView = view.findViewById(R.id.scroll_view)
    }

    private fun initViewModel() {
        if(isNetworkAvailable(requireContext())){
            if (page <= limit) {

                val viewModel=ViewModelProvider(this).get(VideoFeedViewModel::class.java)
                viewModel.getLiveDataObserver().observe(this) {
                    if (it != null) {
                        entireData.addAll(it.data as ArrayList<VideoData>)
                        val reqData = LinkedHashSet(entireData).toMutableList()
                        adapter.addItem(reqData as ArrayList<VideoData>)
                        adapter.notifyDataSetChanged()
                        shimmer.stopShimmerAnimation()
                        shimmer.visibility =View.GONE
                    }
                }

                viewModel.loadListofData(page)
                page++
            }
        }else{
            noNetworkError(requireActivity(),DashboardView::class.java)
        }
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        var haveConnectedWifi = false
        var haveConnectedMobile = false

        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.allNetworkInfo
        for (ni in netInfo) {
            if (ni.typeName.equals(
                    "WIFI",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedWifi = true
            if (ni.typeName.equals(
                    "MOBILE",
                    ignoreCase = true
                )
            ) if (ni.isConnected) haveConnectedMobile = true
        }
        return haveConnectedWifi || haveConnectedMobile
    }

    private fun noNetworkError(activity: Activity, aClass: Class<*>?) {
        AlertDialog.Builder(activity).setTitle("No Internet Connection")
            .setMessage("Please Check Your Internet Connection").setPositiveButton("Retry")
            { _, _ ->
                activity.startActivity(Intent(activity, aClass))
            }.setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }.show()
    }
}