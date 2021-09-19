package com.pavelmaltsev.flickrgallery.data.retrofit

import com.pavelmaltsev.flickrgallery.constants.Constants.Companion.API_KEY
import com.pavelmaltsev.flickrgallery.constants.Constants.Companion.EXTRA_URL
import com.pavelmaltsev.flickrgallery.constants.Constants.Companion.JSON_FORMAT
import com.pavelmaltsev.flickrgallery.constants.Constants.Companion.PHOTOS_PATH
import com.pavelmaltsev.flickrgallery.constants.Flickr
import com.pavelmaltsev.flickrgallery.model.FlickrResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitService {

    @GET("$PHOTOS_PATH$API_KEY$JSON_FORMAT$EXTRA_URL")
    suspend fun getPhotos(
        @Query(Flickr.PAGE) page: Int,
        @Query(Flickr.PER_PAGE) size: Int,
    ): Response<FlickrResponse>
}