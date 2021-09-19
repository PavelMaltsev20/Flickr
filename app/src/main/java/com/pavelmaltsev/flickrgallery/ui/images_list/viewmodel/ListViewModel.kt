package com.pavelmaltsev.flickrgallery.ui.images_list.viewmodel

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.pavelmaltsev.flickrgallery.constants.AppValues.Companion.DEFAULT_FLICKR
import com.pavelmaltsev.flickrgallery.constants.AppValues.Companion.KEY_FLICKR
import com.pavelmaltsev.flickrgallery.data.retrofit.RetrofitRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

class ListViewModel(
    private val retrofitRepository: RetrofitRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val preloadingLength: Long = 2200
    var firstLoading = true

    private val clearListCh = Channel<Unit>(Channel.CONFLATED)
    val photos get() = _photos

    init {
        if (!savedStateHandle.contains(KEY_FLICKR)) {
            savedStateHandle.set(KEY_FLICKR, DEFAULT_FLICKR)
        }
    }

    private var _photos = flowOf(
        clearListCh
            .receiveAsFlow()
            .map { PagingData.empty() },
        savedStateHandle.getLiveData<String>(KEY_FLICKR)
            .asFlow()
            .flatMapLatest { retrofitRepository.getPhotos() }
            .cachedIn(viewModelScope)
    ).flattenMerge(2)
}

