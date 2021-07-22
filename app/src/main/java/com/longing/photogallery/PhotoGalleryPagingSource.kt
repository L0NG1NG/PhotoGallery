package com.longing.photogallery

import android.util.Log
import androidx.paging.PagingSource

private const val TAG = "PagingSource"

class PhotoGalleryPagingSource(private val service: FlickrFetchr) :
    PagingSource<Int, GalleryItem>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GalleryItem> {
        val nextPageNumber = params.key ?: 1
        return try {
            val response = service.fetchPhotos(nextPageNumber)
            Log.d(TAG, "load: --->${response.size}")
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}