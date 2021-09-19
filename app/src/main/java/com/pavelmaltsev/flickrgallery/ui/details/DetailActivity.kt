package com.pavelmaltsev.flickrgallery.ui.details

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.pavelmaltsev.flickrgallery.R
import com.pavelmaltsev.flickrgallery.constants.AppValues.Companion.EXTRA_PHOTO
import com.pavelmaltsev.flickrgallery.constants.AppValues.Companion.PHOTO_FORMAT
import com.pavelmaltsev.flickrgallery.constants.Flickr.Companion.POSTED_USER_ADDRESS
import com.pavelmaltsev.flickrgallery.databinding.ActivityDetailBinding
import com.pavelmaltsev.flickrgallery.model.FlickrPhoto

class DetailActivity : AppCompatActivity() {

    //UI
    private lateinit var binding: ActivityDetailBinding

    //Properties
    private var screenHeight = 0
    private var screenWidth = 0
    private lateinit var photo: FlickrPhoto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!intent.hasExtra(EXTRA_PHOTO))
            return

        initPhoto()
        initScreenSize()
        initListener()
        setData()
    }

    private fun initPhoto() {
        photo = intent.extras?.getSerializable(EXTRA_PHOTO) as FlickrPhoto
    }

    private fun initScreenSize() {
        val displayMetrics = DisplayMetrics()
        display?.getRealMetrics(displayMetrics).apply {
            screenHeight = displayMetrics.heightPixels
            screenWidth = displayMetrics.widthPixels
        }
    }

    private fun initListener() {
        binding.photoDetailsOpenSite.setOnClickListener {
            val siteUrl = "$POSTED_USER_ADDRESS/${photo.owner}/${photo.id}"
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(siteUrl))

            //If current phone doesn't have browser, program will throw exception
            try {
                startActivity(browserIntent)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun setData() {
        setImage()
        setTitle()
    }

    private fun setImage() {
        //If url from Flickr was incorrect or broken it will throw exception
        try {
            val url = photo.url_s.substring(0, photo.url_s.length - 6) + PHOTO_FORMAT

            Glide.with(this)
                .load(url)
                .placeholder(R.drawable.ic_download)
                .error(R.drawable.ic_error)
                .apply(RequestOptions.overrideOf(screenWidth, screenHeight))
                .into(binding.photoDetailsPhoto)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun setTitle() {
        if (photo.title.isNotEmpty()) {
            binding.photoDetailsTitle.apply {
                visibility = View.VISIBLE
                text = photo.title
            }
        } else {
            binding.photoDetailsTitle.visibility = View.GONE
        }
    }
}