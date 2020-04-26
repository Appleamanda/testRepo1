package com.boc.tools.jiraLogin.util;

import com.alibaba.fastjson.JSONObject;
import com.github.kevinsawicki.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class UimUtil
{
    //TOKEN and VERIFY_EXT_RUL need to be changed.
    private static final String VERIFY_EXT_URL = "http://localhost:3001/users";
    private static final String TOKEN = "628a325246ef427142bab6c9e4893ed8";
    private static final Logger log = Logger.getLogger(UimUtil.class);

    public static boolean extVerify(String username, String password)
    {
        if ((StringUtils.isNotEmpty(username)) && (StringUtils.isNotEmpty(password)))
            try {
                long time = System.currentTimeMillis();
                Map paras = new HashMap();
                paras.put("username", username);
                //Password need to be encrypted. paras.put("password", Utils.md5(password));
                paras.put("password", password);
                paras.put("time", String.valueOf(time));
                paras.put("sign", Utils.md5(TOKEN + time + username));
                HttpRequest httpRequest = HttpRequest.post(VERIFY_EXT_URL).connectTimeout(500);
                httpRequest.contentType("application/x-www-form-urlencoded");
                httpRequest.acceptEncoding("UTF-8");
                httpRequest.acceptCharset("UTF-8");
                httpRequest.form(paras);
                String json = httpRequest.body();
                JSONObject jsonObject = JSONObject.parseObject(json);
                if (Integer.parseInt(jsonObject.get("REQ_CODE").toString()) == 1 ) {
                    return true;
                }
                log.warn("ExtVerify invalid username and password! " + jsonObject.toJSONString());
            }
            catch (Exception e) {
                log.error("Error ExtVerify: " + e.getMessage());
            }
        else {
            log.warn("ExtVerify Null Error! username=" + username);
        }
        return false;
    }

    public static boolean isExtUser(String username)
    {
        if (username.startsWith("st-wg-")) {
            return true;
        }
        return false;
    }
}