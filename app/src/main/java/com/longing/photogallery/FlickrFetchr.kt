package com.longing.photogallery

import android.util.Log
import com.google.gson.GsonBuilder
import com.longing.photogallery.api.FlickrApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "FlickrFetchr"

class FlickrFetchr {
    private val flickrApi: FlickrApi

    init {
        val gson = GsonBuilder()
            .registerTypeAdapter(PhotoResponse::class.java, PhotoDeserializer())
            .create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.flickr.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        flickrApi = retrofit.create(FlickrApi::class.java)
    }


    suspend fun fetchPhotos(page: Int = 1): List<GalleryItem> {
        Log.d(TAG, "fetchPhotos: ")
        return flickrApi.fetchPhotos(page).galleryItems
    }
}