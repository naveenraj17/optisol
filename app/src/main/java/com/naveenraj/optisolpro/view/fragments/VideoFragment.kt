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
import com.naveenraj.optisolpro.utils.VideoAdapter
import com.naveenraj.optisolpro.view.DashboardView
import com.naveenraj.optisolpro.view_model.VideoFeedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment() {

    private lateinit var videoRecyclerview: RecyclerView
    private lateinit var recyclerAdapter: VideoAdapter
    private lateinit var scrollView: NestedScrollView
    private lateinit var shimmer: ShimmerFrameLayout
    private var entireData: ArrayList<VideoData> =ArrayList()
    var page = 1
    var isLoading = false
    var limit = 2
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_video, container, false)
        videoRecyclerview = view.findViewById(R.id.video_recycler)
        shimmer = view.findViewById(R.id.shimmer_view_container)
        scrollView = view.findViewById(R.id.scroll_view)
        shimmer.startShimmerAnimation()
        shimmer.visibility =View.VISIBLE
        layoutManager = LinearLayoutManager(requireContext())

        videoRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
//        recyclerAdapter = VideoAdapter(entireData)
//        videoRecyclerview.adapter = recyclerAdapter
        if(isNetworkAvailable(requireContext())){
            initViewModel(false)
        }else{
            noNetworkError(requireActivity(),DashboardView::class.java)
        }

        scrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            // on scroll change we are checking when users scroll as bottom.
            if (scrollY == v.getChildAt(0).measuredHeight - v.measuredHeight) {
                // in this method we are incrementing page number,
                // making progress bar visible and calling get data method.
                page++
                initViewModel(false)
//                loadingPB.setVisibility(View.VISIBLE)
//                getDataFromAPI(page, limit)
            }
        })

        return view
    }

    private fun initViewModel(status: Boolean) {
        isLoading = true
//        if(!status)
        if (page > limit) {

//            loadingPB.setVisibility(View.GONE);
            return;
        }else{

            val viewModel=ViewModelProvider(this).get(VideoFeedViewModel::class.java)
            viewModel.getLiveDataObserver().observe(this,{
                if(it !=null){
                    videoRecyclerview.setHasFixedSize(true)
                    var data = it.data as ArrayList<VideoData>
                    entireData.addAll(data)


//                limit = it.total!!
                videoRecyclerview.adapter = VideoAdapter(
                    entireData)
                recyclerAdapter = VideoAdapter(entireData)
//                    recyclerAdapter.addData(data)
                    shimmer.visibility =View.GONE
                    shimmer.stopShimmerAnimation()


                }
            })

            viewModel.loadListofData(page)
        }


    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

    }
    fun isNetworkAvailable(context: Context): Boolean {
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

    fun noNetworkError(activity: Activity, aClass: Class<*>?) {
        AlertDialog.Builder(activity).setTitle("No Internet Connection")
            .setMessage("Please Check Your Internet Connection").setPositiveButton("Retry")
            { dialog, which ->
                activity.startActivity(Intent(activity, aClass))
            }.setNegativeButton("Cancel") { dialog, which -> dialog.dismiss() }.show()
    }
}