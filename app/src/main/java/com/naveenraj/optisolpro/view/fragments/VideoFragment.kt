package com.naveenraj.optisolpro.view.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
    private lateinit var shimmer: ShimmerFrameLayout
    var page = 1
    var isLoading = false
    var limit = 1
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_video, container, false)
        videoRecyclerview = view.findViewById(R.id.video_recycler)
        shimmer = view.findViewById(R.id.shimmer_view_container)
        shimmer.startShimmerAnimation()
        shimmer.visibility =View.VISIBLE
        layoutManager = LinearLayoutManager(requireContext())

        videoRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
        recyclerAdapter = VideoAdapter()
        videoRecyclerview.adapter = recyclerAdapter
        videoRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {


                val visibleItemCount = layoutManager.childCount
                val pastVisibleItemCount = layoutManager.findFirstVisibleItemPosition()
                val total = recyclerAdapter.itemCount
                if( !isLoading &&total < limit ){
                    if(visibleItemCount+pastVisibleItemCount>=total){
                        page++
                        if(isNetworkAvailable(requireContext())){
                            initViewModel(false)
                        }else{
                            noNetworkError(requireActivity(),DashboardView::class.java)
                        }
                    }
                }
                super.onScrolled(recyclerView, dx, dy)
            }
        })
        if(isNetworkAvailable(requireContext())){
            initViewModel(false)
        }else{
            noNetworkError(requireActivity(),DashboardView::class.java)
        }


        return view
    }

    private fun initViewModel(status: Boolean) {
        isLoading = true
//        if(!status)

        val viewModel=ViewModelProvider(this).get(VideoFeedViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this,{
            if(it !=null){
                videoRecyclerview.setHasFixedSize(true)
                var data = it.data as ArrayList<VideoData>
                limit = it.total!!
//                videoRecyclerview.adapter = VideoAdapter(
//                    data)
//                recyclerAdapter = VideoAdapter(data)
                recyclerAdapter.addData(data)
                shimmer.visibility =View.GONE
                shimmer.stopShimmerAnimation()


            }
        })

        viewModel.loadListofData(page)
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