package com.mcl.net.util

import android.widget.Toast
import com.mcl.net.NetAppContext

/**
 * 公司：坤创科技有限公司
 * 作者：Android 孟从伦
 * 创建时间：2021/5/25
 * 功能描述：
 */
object HttpToast {
    fun showToast(msg:String?=null){
        if (msg == null)return

        Toast.makeText(
            NetAppContext.getContext(),
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}