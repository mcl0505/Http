package com.mcl.net.http

import com.mcl.net.config.NetBase
import com.mcl.net.util.SSLContextUtil
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Describe:
 * 服务代理生成
 *
 *  在application中必须先初始化NetConfig
 *  Map<String, String> heads = new HashMap<>();
 *  heads.put("1","head");
 *
 *  NetConfig config = new NetConfig.Builder()
 *          .setBaseUrl("http://192.168.137.1:8085/")
 *          .setDefaultTimeout(10_000)
 *          .setHeads(heads)
 *          .addInterceptor(interceptor)
 *          .enableHttps(true)
 *          .build();
 *   config.initContext(this);
 * @author zhouhuan
 * @Date 2020/12/1
 */

class RetrofitManager {
    private val okHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private val retrofit: Retrofit

    private val logger: HttpLoggingInterceptor
        get() {
            val loggingInterceptor = HttpLoggingInterceptor()
            if (NetBase.isLog) {
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BODY }
            }else{
                loggingInterceptor.apply { level = HttpLoggingInterceptor.Level.BASIC }
            }
            return loggingInterceptor
        }

    init {
//        okHttpBuilder.addInterceptor(logger)
        // 添加请求拦截器
        okHttpBuilder.addInterceptor(HttpTokenInterceptor())
        // 添加配置增加拦截器
        val interceptors = NetBase.interceptors
        if (interceptors.isNotEmpty()) {
            interceptors.forEach { okHttpBuilder.addInterceptor(it) }
        }
        // 添加网络拦截器
        val networkInterceptors = NetBase.networkInterceptors
        if (networkInterceptors.isNotEmpty()) {
            networkInterceptors.forEach { okHttpBuilder.addInterceptor(it) }
        }

        okHttpBuilder.connectTimeout(NetBase.defaultTimeout.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(NetBase.defaultTimeout.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(NetBase.defaultTimeout.toLong(), TimeUnit.SECONDS)
        okHttpBuilder.retryOnConnectionFailure(true)

        // 判断是否启用 https
        if (NetBase.enableHttps) {
            //给client的builder添加了增加可以忽略SSL
            val sslParams = SSLContextUtil.getSslSocketFactory()
            okHttpBuilder.sslSocketFactory(sslParams.sSLSocketFactory!!, sslParams.trustManager!!)
            okHttpBuilder.hostnameVerifier(SSLContextUtil.UnSafeHostnameVerifier)
        }

        val client = okHttpBuilder.build()

        retrofit = Retrofit.Builder()
            .baseUrl(NetBase.baseUrl)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .build()
    }

    fun <S> create(serviceClass: Class<S>): S = retrofit.create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)

    private fun getMoshi(): Moshi {
        return Moshi.Builder()
            .add(MyKotlinJsonAdapterFactory())
            .add(MyStandardJsonAdapters.FACTORY)
            .build()
    }
}
