package com.boc.tools.jiraLogin.util;

import java.security.MessageDigest;
import javax.servlet.http.HttpServletRequest;

public class Utils
{
    public static final String md5(String message)
    {
        char[] md5String = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try
        {
            byte[] btInput = message.getBytes();

            MessageDigest mdInst = MessageDigest.getInstance("MD5");

            mdInst.update(btInput);

            byte[] md = mdInst.digest();

            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[(k++)] = md5String[(byte0 >>> 4 & 0xF)];
                str[(k++)] = md5String[(byte0 & 0xF)];
            }

            return new String(str); } catch (Exception e) {
        }
        return null;
    }
}
