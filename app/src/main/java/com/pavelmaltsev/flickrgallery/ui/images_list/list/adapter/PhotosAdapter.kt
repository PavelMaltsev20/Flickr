package com.pavelmaltsev.flickrgallery.ui.images_list.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.pavelmaltsev.flickrgallery.databinding.ItemPhotoBinding
import com.pavelmaltsev.flickrgallery.model.FlickrPhoto
import com.pavelmaltsev.flickrgallery.ui.images_list.list.callback.OnItemClickListener
import com.pavelmaltsev.flickrgallery.ui.images_list.list.types.PhotosViewHolder

class PhotosAdapter(
    private val listener: OnItemClickListener
) : PagingDataAdapter<FlickrPhoto, PhotosViewHolder>(PHOTO_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        return PhotosViewHolder(
            ItemPhotoBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean =
                oldItem == newItem
        }
    }
}