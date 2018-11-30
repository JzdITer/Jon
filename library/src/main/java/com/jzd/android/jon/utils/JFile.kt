package com.jzd.android.jon.utils

import java.io.*

object JFile
{

    /**
     * 复制文件到指定位置
     *
     * @param resFile 资源文件
     * @param targetFile  目标文件
     * @return 是否复制成功
     */
    @Throws(IOException::class)
    fun copy(resFile: File, targetFile: File): Boolean
    {
        val bn: Boolean
        var inBuff: BufferedInputStream? = null
        var outBuff: BufferedOutputStream? = null
        try
        {
            // 新建文件输入流并对它进行缓冲
            inBuff = BufferedInputStream(FileInputStream(resFile))

            // 新建文件输出流并对它进行缓冲
            outBuff = BufferedOutputStream(FileOutputStream(targetFile))

            // 缓冲数组
            val b = ByteArray(1024 * 5)
            var len: Int
            do
            {
                len = inBuff.read(b)
                if(len != -1)
                {
                    outBuff.write(b, 0, len)
                } else
                {
                    break
                }
            }
            while(true)

            // 刷新此缓冲的输出流
            outBuff.flush()
            bn = true
        } finally
        {
            // 关闭流
            if(inBuff != null)
            {
                inBuff.close()
            }
            if(outBuff != null)
            {
                outBuff.close()
            }
        }
        return bn
    }
}