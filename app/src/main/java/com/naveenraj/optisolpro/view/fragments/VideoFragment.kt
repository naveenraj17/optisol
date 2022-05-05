package com.naveenraj.optisolpro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.naveenraj.optisolpro.R
import com.naveenraj.optisolpro.model.VideoReponse
import com.naveenraj.optisolpro.utils.VideoAdapter
import com.naveenraj.optisolpro.view_model.VideoFeedViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class VideoFragment : Fragment() {

    private lateinit var videoRecyclerview: RecyclerView
    private lateinit var recyclerAdapter: VideoAdapter
    var page = 1
    var isLoading = false
    var limit = 6
    lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_video, container, false)
        videoRecyclerview = view.findViewById(R.id.video_recycler)
        layoutManager = LinearLayoutManager(requireContext())

        videoRecyclerview.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
//        recyclerAdapter = VideoAdapter()


        initViewModel()


        return view
    }

    private fun initViewModel() {
        val viewModel=ViewModelProvider(this).get(VideoFeedViewModel::class.java)
        viewModel.getLiveDataObserver().observe(this,{
            if(it !=null){
                videoRecyclerview.setHasFixedSize(true)
                var data = it.data as ArrayList<VideoReponse>
                videoRecyclerview.adapter = VideoAdapter(
                    data)
                recyclerAdapter = VideoAdapter(data)
            }
        })



        viewModel.loadListofData(page)
    }


    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)

    }


}