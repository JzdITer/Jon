package com.jzd.android.jon.utils;

import java.security.MessageDigest;
import java.util.Arrays;

public class JMD5
{

    public static String toMD5(String plainText)
    {
        try
        {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            StringBuilder buf = new StringBuilder("");
            for(byte aB : b)
            {
                i = aB;
                if(i < 0)
                {
                    i += 256;
                }
                if(i < 16)
                {
                    buf.append("0");
                }
                buf.append(Integer.toHexString(i));
            }
            // System.out.println("32位: " + buf.toString());// 32位的加密
            // System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
            return buf.toString();
        } catch(Exception e)
        {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * @param noncestr  随机字符串
     * @param timestamp 时间戳
     */
    public static String getStringURLMosaic(String openId, String noncestr, String timestamp, String masterSecret)
    {
        String[] beforeSignaTure = new String[]{"nonceStr=" + noncestr, "openId=" + openId, "timestamp=" + timestamp};
        Arrays.sort(beforeSignaTure);
        StringBuilder content = new StringBuilder();
        for(String str : beforeSignaTure)
        {
            if(content.length() == 0)
            {
                content.append(str);
            } else
            {
                content.append("&")
                        .append(str);
            }
        }
        return toMD5(content.toString() + masterSecret);
    }

    public static String getStringPassworld()
    {
        int firstNum = ((int) (Math.random() * 10)) % 9 + 1;
        String lastNum = "" + firstNum;
        for(int i = 0; i < firstNum; i++)
        {
            lastNum += (int) (Math.random() * 10);
        }
        return lastNum;
    }

    public static String getRandomString()
    {
        String randomString = "";
        for(int i = 0; i < 30; i++)
        {
            randomString += (int) (Math.random() * 10);
        }
        return randomString;
    }
}
