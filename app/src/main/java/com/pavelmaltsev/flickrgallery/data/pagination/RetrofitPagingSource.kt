package com.pavelmaltsev.flickrgallery.data.pagination

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.pavelmaltsev.flickrgallery.constants.Flickr.Companion.PAGE_SIZE
import com.pavelmaltsev.flickrgallery.data.retrofit.RetrofitService
import com.pavelmaltsev.flickrgallery.model.FlickrPhoto
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 0

class RetrofitPagingSource(
    private val service: RetrofitService
) : PagingSource<Int, FlickrPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FlickrPhoto> {
        val page = params.key ?: STARTING_PAGE_INDEX

        return try {
            val response = service.getPhotos(page, PAGE_SIZE)
            val photosList = response.body()!!.photos.photo

            LoadResult.Page(
                data = photosList,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page,
                nextKey = if (photosList.isEmpty()) null else page + 1
            )
        } catch (e: IOException) {
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FlickrPhoto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}