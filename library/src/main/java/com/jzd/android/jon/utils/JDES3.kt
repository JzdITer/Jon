package com.jzd.android.jon.utils

import android.annotation.SuppressLint
import java.security.Key
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.DESedeKeySpec
import javax.crypto.spec.IvParameterSpec

@SuppressLint("GetInstance")
class JDES3 private constructor()
{
    companion object
    {
        const val mAlgorithm = "desede"
        lateinit var mKey: ByteArray
        var mKeyIv: ByteArray? = null
        private lateinit var mChartName: String
        fun getInstance(key: ByteArray, keyIv: ByteArray?, chartName: String): JDES3
        {
            mKey = key
            mKeyIv = keyIv
            mChartName = chartName
            return JDES3()
        }
    }

    /**
     * ECB加密,Android不建议使用，不安全
     */
    fun encodeECB(by: ByteArray): ByteArray?
    {
        var secret: Key? = null
        val spec = DESedeKeySpec(mKey)
        val keyFactory = SecretKeyFactory.getInstance(mAlgorithm)
        secret = keyFactory.generateSecret(spec)

        val cipher = Cipher.getInstance("desede/ECB/PKCS5Padding")
        cipher.init(Cipher.ENCRYPT_MODE, secret)
        return cipher.doFinal(by)
    }

    /**
     * ECB解密
     */
    fun decodeECB(by: ByteArray): ByteArray?
    {
        var secret: Key? = null
        val spec = DESedeKeySpec(mKey)
        val keyFactory = SecretKeyFactory.getInstance(mAlgorithm)
        secret = keyFactory.generateSecret(spec)

        val cipher = Cipher.getInstance("desede/ECB/PKCS5Padding")
        cipher.init(Cipher.DECRYPT_MODE, secret)

        return cipher.doFinal(by)
    }

    /**
     * CBC加密
     */
    fun encodeCBC(by: ByteArray): ByteArray?
    {
        var secret: Key? = null
        val spec = DESedeKeySpec(mKey)
        val keyFactory = SecretKeyFactory.getInstance(mAlgorithm)
        secret = keyFactory.generateSecret(spec)

        val cipher = Cipher.getInstance("desede/CBC/PKCS5Padding")
        val ips = IvParameterSpec(mKeyIv)

        cipher.init(Cipher.DECRYPT_MODE, secret, ips)
        return cipher.doFinal(by)
    }


    /**
     * CBC解密
     */
    fun decodeCBC(by: ByteArray): ByteArray?
    {
        var secret: Key? = null
        val spec = DESedeKeySpec(mKey)
        val keyFactory = SecretKeyFactory.getInstance("desede")
        secret = keyFactory.generateSecret(spec)

        val cipher = Cipher.getInstance("desede/CBC/PKCS5Padding")
        val ips = IvParameterSpec(mKeyIv)
        cipher.init(Cipher.DECRYPT_MODE, secret, ips)

        return cipher.doFinal(by)
    }

}

