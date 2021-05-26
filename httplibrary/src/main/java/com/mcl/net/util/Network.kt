package com.mcl.net.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：检测当前请求的网络状态信息
 */
class Network constructor(val context: Context) : NetworkConnectivity {

    override fun isConnected(): Boolean {
        val info = getNetworkInfo()
        return info != null && info.isConnected
    }

    override fun getNetworkInfo(): NetworkInfo? {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo
    }
}

interface NetworkConnectivity {
    fun getNetworkInfo(): NetworkInfo?
    fun isConnected(): Boolean
}