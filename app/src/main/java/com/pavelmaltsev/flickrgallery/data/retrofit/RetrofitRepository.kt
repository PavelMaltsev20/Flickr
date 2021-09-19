package com.pavelmaltsev.flickrgallery.data.retrofit

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.pavelmaltsev.flickrgallery.constants.Flickr.Companion.PAGE_SIZE
import com.pavelmaltsev.flickrgallery.data.pagination.RetrofitPagingSource

class RetrofitRepository {

    private val LOCK = Any()
    private var instance: RetrofitRepository? = null

    fun instance(): RetrofitRepository {
        synchronized(LOCK) {
            if (instance == null) {
                instance = RetrofitRepository()
            }
            return instance!!
        }
    }

    fun getPhotos() = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            enablePlaceholders = true
        ),
        pagingSourceFactory = { RetrofitPagingSource(RetrofitInstance.getRetrofitService) }
    ).flow

}