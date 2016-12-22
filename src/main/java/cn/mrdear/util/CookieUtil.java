package cn.mrdear.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static Logger logger = LoggerFactory.getLogger(CookieUtil.class);
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
            cookie.setPath("/");
            response.addCookie(cookie);
            logger.debug("写入cookies:"+name+"---值:"+value);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
