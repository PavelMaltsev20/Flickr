package com.pavelmaltsev.flickrgallery.ui.images_list

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.pavelmaltsev.flickrgallery.constants.AppValues.Companion.EXTRA_PHOTO
import com.pavelmaltsev.flickrgallery.data.retrofit.RetrofitRepository
import com.pavelmaltsev.flickrgallery.databinding.ActivityListBinding
import com.pavelmaltsev.flickrgallery.model.FlickrPhoto
import com.pavelmaltsev.flickrgallery.ui.details.DetailActivity
import com.pavelmaltsev.flickrgallery.ui.images_list.list.adapter.PhotosAdapter
import com.pavelmaltsev.flickrgallery.ui.images_list.list.callback.OnItemClickListener
import com.pavelmaltsev.flickrgallery.ui.images_list.viewmodel.ListViewModel
import com.pavelmaltsev.flickrgallery.utils.NetworkReceiver
import com.pavelmaltsev.flickrgallery.utils.OnNetworkListener
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ListActivity : AppCompatActivity(), OnItemClickListener, OnNetworkListener {

    private lateinit var binding: ActivityListBinding
    private var photosAdapter = PhotosAdapter(this)
    private var networkReceiver: NetworkReceiver? = NetworkReceiver(this, this)

    private val viewModel: ListViewModel by viewModels {
        object : AbstractSavedStateViewModelFactory(this, null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                val repo = RetrofitRepository().instance()
                return ListViewModel(repo, handle) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showPreloadingPlaceholder()
        initAdapter()
        initNetworkListener()
        initSwipeToRefresh()
    }

    //While downloading data from the internet user
    //will see beautiful shimmer placeholder.
    private fun showPreloadingPlaceholder() {
        if (!viewModel.firstLoading)
            return

        binding.listPreload.preloadParent.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            binding.listPreload.preloadParent.visibility = View.GONE
        }, viewModel.preloadingLength)

        viewModel.firstLoading = false
    }

    private fun initNetworkListener() {
        networkReceiver?.isInternetAvailable()
    }

    private fun initAdapter() {
        binding.list.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = photosAdapter
        }

        lifecycleScope.launchWhenCreated {
            viewModel.photos.collectLatest {
                photosAdapter.submitData(it)
            }
        }
    }

    //When user pull down from the top of the screen it will refresh data
    private fun initSwipeToRefresh() {
        binding.listRefreshLayout.setOnRefreshListener {
            photosAdapter.refresh()
            binding.listRefreshLayout.isRefreshing = false
        }
    }

    override fun onPhotoClicked(photo: FlickrPhoto) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(EXTRA_PHOTO, photo)
        startActivity(intent)
    }

    override fun networkStatus(available: Boolean) {
        MainScope().launch {
            if (available) {
                binding.listConnectionLost.connectionParent.visibility = View.GONE
            } else {
                binding.listConnectionLost.connectionParent.visibility = View.VISIBLE
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //Avoiding memory leaks
        networkReceiver = null
    }
}
