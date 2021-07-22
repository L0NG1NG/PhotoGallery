package com.longing.photogallery;

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.longing.photogallery.api.FlickrApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val TAG = "PhotoGalleryFragment"

class PhotoGalleryFragment : Fragment() {
    private lateinit var photoGalleryViewModel: PhotoGalleryViewModel
    private lateinit var photoRecyclerView: RecyclerView
    private var loadJob: Job? = null
    private val adapter = PhotoAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_photo_gallery, container, false)

        photoRecyclerView = view.findViewById(R.id.photo_recycler_view)
//        val spanCounts = resources.getInteger(R.integer.gallery_span_counts)
//        photoRecyclerView.layoutManager = GridLayoutManager(context, spanCounts)
        val globalLayoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val counts =
                    photoRecyclerView.width / resources.getDimensionPixelSize(R.dimen.min_span_width)
                photoRecyclerView.layoutManager = GridLayoutManager(context, counts)
                photoRecyclerView.viewTreeObserver.removeOnGlobalLayoutListener(this)
                Log.d(TAG, "onGlobalLayout: -->$counts")
            }

        }
        photoRecyclerView.viewTreeObserver
            .addOnGlobalLayoutListener(globalLayoutListener)
        photoRecyclerView.adapter = adapter

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        photoGalleryViewModel = ViewModelProvider(this).get(PhotoGalleryViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadJob?.cancel()
        loadJob = lifecycleScope.launch {
            photoGalleryViewModel.loadPhoto().collectLatest {
                adapter.submitData(it)
            }
        }
    }


    companion object {
        fun newInstance() = PhotoGalleryFragment()

    }


}

