package com.pavelmaltsev.flickrgallery.ui.images_list.list.types

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.pavelmaltsev.flickrgallery.databinding.ItemPhotoBinding
import com.pavelmaltsev.flickrgallery.model.FlickrPhoto
import com.pavelmaltsev.flickrgallery.ui.images_list.list.callback.OnItemClickListener

class PhotosViewHolder(
    private val binding: ItemPhotoBinding,
    private val listener: OnItemClickListener
) :
    RecyclerView.ViewHolder(binding.root) {

    private var imageView = binding.itemPhotoImage
    private var title = binding.itemPhotoTitle

    fun bind(photo: FlickrPhoto) {
        //Displaying image
        Glide.with(binding.root.context)
            .load(photo.url_s)
            .placeholder(getShimmerDrawable())
            .into(imageView)

        //Displaying title
        if (photo.title.isNotEmpty()) {
            title.visibility = View.VISIBLE
            title.text = photo.title
        } else {
            title.visibility = View.GONE
        }

        //Init on photo click listener
        binding.root.setOnClickListener {
            listener.onPhotoClicked(photo)
        }
    }

    private fun getShimmerDrawable(): ShimmerDrawable {
        val shimmer = Shimmer.AlphaHighlightBuilder()
            .setDuration(1800)
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.6f)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        return ShimmerDrawable().apply {
            setShimmer(shimmer)
        }
    }
}
