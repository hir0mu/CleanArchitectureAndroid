package com.hir0mu.cleanarchitecture.util

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.hir0mu.cleanarchitecture.data.NetworkManager

object NetworkManagerImpl : NetworkManager {
    override val isConnected: Boolean
        get() = _isConnected

    private var _isConnected: Boolean = true

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            super.onLost(network)
            _isConnected = false
        }

        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            _isConnected = true
        }
    }

    fun observe(connectivityManager: ConnectivityManager) {
        if (connectivityManager.activeNetwork == null) {
            _isConnected = false
        }
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }
}
