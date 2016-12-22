package cn.mrdear.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * 操作cookies的工具类
 * @author Niu Li
 * @date 2016/12/22
 */
public class CookieUtil {

    /**
     * 写入cookies
     * @param name 键
     * @param value 值
     */
    public static void addCookie(HttpServletResponse response,String name,String value){
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name,value);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
