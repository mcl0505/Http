package com.mcl.net.http

import android.text.TextUtils
import com.easy.library.base.http.Encoding
import com.easy.library.base.http.contentTypeValue
import com.mcl.net.error.*
import com.mcl.net.util.HttpToast
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Response
import okhttp3.ResponseBody
import org.json.JSONObject
import java.nio.charset.Charset

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：相应数据拦截器
 */
class ResponseInterceptor : Interceptor {
    private val TAG = ResponseInterceptor::class.java.simpleName
    val errorManager: ErrorManager = ErrorManager(ErrorMapper())

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val body = bufferBody(response)

        // 网络相应不成功返回
        if (!response.isSuccessful) {
            val ex = ApiException(response.code)
            ex.msg = errorManager.getError(response.code).description
            HttpToast.showToast(ex.message)
            throw ex
        }

        return try {
            if (TextUtils.isEmpty(body) || "null".equals(body, ignoreCase = true)) {
                throw ApiException(NULL_DATA, errorManager.getError(NULL_DATA).description)
            }
            val jsonObject = JSONObject(body)
            val status = jsonObject.getString("status").toInt()
            val message = jsonObject.getString("message")
            if (0 == status) {
                response.newBuilder()
                    .body(ResponseBody.create(contentTypeValue.toMediaTypeOrNull(), body))
                    .build()
            } else {
                throw ApiException(status, message ?: "")
            }
        } catch (e: Exception) {
            throw ApiException(PARSE_ERROR, errorManager.getError(PARSE_ERROR).description, e)
        }
    }

    fun bufferBody(response: Response): String {
        val source = response.body!!.source()
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer()
        return buffer.clone().readString(Charset.forName(Encoding))
    }
}
