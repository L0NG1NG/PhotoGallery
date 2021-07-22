package com.longing.photogallery

import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "PhotoAdapter"

class PhotoAdapter :
    PagingDataAdapter<GalleryItem, PhotoAdapter.PhotoHolder>(GalleryDiffCallback()) {
    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val galleryItem = getItem(position)
        if (galleryItem != null) {
            holder.bindTitle(galleryItem.title)
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoHolder {
        val textView = TextView(parent.context)
        return PhotoHolder(textView)
    }


    class PhotoHolder(itemTextView: TextView) : RecyclerView.ViewHolder(itemTextView) {
        val bindTitle: (CharSequence) -> Unit = itemTextView::setText
    }

    private class GalleryDiffCallback : DiffUtil.ItemCallback<GalleryItem>() {
        override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
            return oldItem == newItem
        }
    }
}