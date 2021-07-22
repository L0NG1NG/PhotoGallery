package com.longing.photogallery.api

import com.longing.photogallery.BuildConfig
import com.longing.photogallery.PhotoResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FlickrApi {
    //真长 yue~
    @GET(
        "services/rest/?method=flickr.interestingness.getList" +
                "&api_key=${BuildConfig.API_KEY}" +
                "&format=json" +
                "&nojsoncallback=1" +
                "&extras=url_s"
    )
    suspend fun fetchPhotos(@Query("page") page: Int): PhotoResponse
}