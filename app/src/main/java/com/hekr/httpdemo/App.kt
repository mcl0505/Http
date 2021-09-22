package com.hekr.httpdemo

import android.app.Application
import com.mcl.net.config.NetConfig

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
//        val config = NetConfig.Builder()
//            .setBaseUrl("http://192.168.2.62:8080/app/")
//            .isLog(true)
//            .build()
//        config.initContext(this)
    }
}