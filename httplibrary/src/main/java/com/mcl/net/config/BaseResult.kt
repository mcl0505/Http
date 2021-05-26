package com.mcl.net.config

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：根据后台返回数据结构使用
 */
sealed class BaseResult<T>(
        val data: T? = null,
        val code: Int? = null,
        val msg:String?=null
) {
    class Success<T>(data: T) : BaseResult<T>(data)
    class Loading<T>(data: T? = null) : BaseResult<T>(data)
    class DataError<T>(errorCode: Int,msg:String) : BaseResult<T>(null, errorCode,msg)

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is DataError -> "Error[exception=$code]"
            is Loading<T> -> "Loading"
        }
    }
}
