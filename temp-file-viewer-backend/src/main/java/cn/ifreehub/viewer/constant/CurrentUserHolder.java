package cn.ifreehub.viewer.constant;

import java.util.HashMap;
import java.util.Map;

public class CurrentUserHolder {
    public static ThreadLocal<Map<String,String>> current = new ThreadLocal<Map<String,String>>();

    /**
     * 设置登录用户名
     * @param userName
     */
    public static void setUserName(String userName){
        Map<String, String> map = current.get();
        if (map == null){
            map = new HashMap<>();
            map.put("userName",userName);
            current.set(map);
        }else {
            if (map.get("userName") == null){
                map.put("userName",userName);
            }
        }
    }

    /**
     * 获取登录用户名
     * @return
     */
    public static String getUserName(){

        Map<String, String> map = current.get();
        if (map == null){
            return null;
        }
        return map.get("userName");
    }

}
