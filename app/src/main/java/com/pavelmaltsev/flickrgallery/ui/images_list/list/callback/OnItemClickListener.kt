package com.pavelmaltsev.flickrgallery.ui.images_list.list.callback

import com.pavelmaltsev.flickrgallery.model.FlickrPhoto

interface OnItemClickListener {
    fun onPhotoClicked(photo: FlickrPhoto)
}