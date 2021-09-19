package com.pavelmaltsev.flickrgallery.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network

class NetworkReceiver(private var listener: OnNetworkListener, private val context: Context) {

      fun isInternetAvailable() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivityManager.activeNetwork == null) {
            listener.networkStatus(false)
        }

        connectivityManager.registerDefaultNetworkCallback(object :
            ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                    listener.networkStatus(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                    listener.networkStatus(false)
            }
        })
    }
}

interface OnNetworkListener {
    fun networkStatus(available: Boolean)
}
