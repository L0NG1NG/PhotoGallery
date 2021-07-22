package com.longing.photogallery

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

class PhotoGalleryViewModel : ViewModel() {
    private val pager = Pager(
        config = PagingConfig(pageSize = 100),
        pagingSourceFactory = { PhotoGalleryPagingSource(FlickrFetchr()) }

    )


    fun loadPhoto(): Flow<PagingData<GalleryItem>> = pager.flow
}