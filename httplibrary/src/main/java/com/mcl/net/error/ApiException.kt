package com.mcl.net.error

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：接口异常
 */
class ApiException constructor(
    var code: Int,
    var msg: String = "",
    var e: Throwable? = null
) : Exception(msg, e)