package com.mcl.net.http

import com.easy.library.base.http.contentType
import com.easy.library.base.http.contentTypeValue
import com.mcl.net.config.NetConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：请求头拦截器
 */
class RequestInterceptor : Interceptor {
    private val TAG = RequestInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val url = original.url.toString()

        val requestBuiler = original.newBuilder()
            // .removeHeader("User-Agent")
            // .addHeader("User-Agent", "dh-net-okhttp")
            .header(contentType, contentTypeValue)
            .method(original.method, original.body)
        //设置请求头
        val heads = NetConfig.getHeads()
        if (!heads.isEmpty()) {
            for (head in heads) {
                requestBuiler.addHeader(head.key, head.value.toString())
            }
        }

        val build = requestBuiler.build()
        return chain.proceed(build)
    }
}