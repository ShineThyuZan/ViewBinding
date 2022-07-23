package com.galaxy_techno.buyer.common

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object UnAvailable : NetworkStatus()
}