package com.mcl.net.config

import android.content.Context
import com.mcl.net.NetAppContext
import okhttp3.Interceptor

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：网络配置
 */
class NetConfig private constructor(builder: Builder) {

    init {
        baseUrl = builder.baseUrl
        defaultTimeout = builder.defaultTimeout
        mToken = builder.mToken
        mTokenKey = builder.mTokenKey
        interceptors = builder.interceptors
        networkInterceptors = builder.networkInterceptors
        heads = builder.heads
        enableHttps = builder.enableHttps
        isLog = builder.isLog
    }

    companion object {
        /**
         * baseUrl
         */
        private var baseUrl: String = "";

        fun getBaseUrl(): String = baseUrl

        /**
         * 连接超时
         */
        private var defaultTimeout: Int = 0;

        fun getDefaultTimeout(): Int = defaultTimeout

        /**
         * token
         */
        private var mToken: String? = ""

        fun getToken(): String = if (mToken == null) "" else mToken!!

        /**
         * tokenKey
         */
        private var mTokenKey: String = "token"

        fun getTokenKey(): String = mTokenKey

        /**
         * 设置拦截器
         */
        private var interceptors = ArrayList<Interceptor>()

        fun getInterceptors(): List<Interceptor> = interceptors

        /**
         * 设置拦截器
         */
        private var networkInterceptors = ArrayList<Interceptor>()

        fun getNetworkInterceptors(): List<Interceptor> = networkInterceptors

        /**
         * 添加头消息，也可以在自己的拦截器中添加，就不用设置这个了
         */
        private var heads = HashMap<String, Any>()

        fun getHeads(): Map<String, Any> = heads

        /**
         * 启用https
         */
        private var enableHttps: Boolean = false

        fun isEnableHttps(): Boolean = enableHttps

        /**
         * 启用日志信息
         */
        private var isLog:Boolean = false

        fun isLog():Boolean = isLog
    }

    /**
     * 初始化上下文
     */
    fun initContext(context: Context) {
        NetAppContext.init(context)
    }

    open class Builder {
        internal var baseUrl: String = ""
        internal var defaultTimeout: Int = 6_000
        internal var mToken: String? = ""
        internal var mTokenKey: String = "token"
        internal var interceptors = ArrayList<Interceptor>()
        internal var networkInterceptors = ArrayList<Interceptor>()
        internal var heads = HashMap<String, Any>()
        internal var enableHttps = false
        internal var isLog = false

        open fun setBaseUrl(baseUrl: String): Builder = apply {
            this.baseUrl = baseUrl
        }

        open fun setDefaultTimeout(defaultTimeout: Int): Builder = apply {
            this.defaultTimeout = defaultTimeout
        }

        open fun setToken(token: String?=null): Builder = apply {
            this.mToken = token
        }

        open fun setTokenKey(tokenKey: String): Builder = apply {
            this.mTokenKey = tokenKey
        }

        open fun addInterceptor(interceptor: Interceptor): Builder = apply {
            this.interceptors.add(interceptor)
        }

        open fun addNetworkInterceptors(interceptor: Interceptor): Builder = apply {
            this.networkInterceptors.add(interceptor)
        }

        open fun setHeads(headers: Map<String, Any>): Builder = apply {
            this.heads.putAll(headers)
        }

        open fun enableHttps(enableHttps: Boolean): Builder = apply {
            this.enableHttps = enableHttps
        }

        open fun isLog(isLog:Boolean):Builder = apply {
            this.isLog = isLog
        }

        open fun build(): NetConfig = NetConfig(this)
    }

    init {
        baseUrl = builder.baseUrl
        defaultTimeout = builder.defaultTimeout
        heads = builder.heads
        interceptors = builder.interceptors
        networkInterceptors = builder.networkInterceptors
        enableHttps = builder.enableHttps
        isLog = builder.isLog
        mTokenKey = builder.mTokenKey
        mToken = builder.mToken
    }
}