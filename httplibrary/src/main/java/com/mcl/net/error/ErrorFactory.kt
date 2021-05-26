package com.mcl.net.error

interface ErrorFactory {
    fun getError(errorCode: Int): Error
}
