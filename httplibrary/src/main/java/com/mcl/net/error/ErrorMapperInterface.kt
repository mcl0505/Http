package com.mcl.net.error

interface ErrorMapperInterface {
    fun getErrorString(errorId: Int): String
    val errorsMap: Map<Int, String>
}
