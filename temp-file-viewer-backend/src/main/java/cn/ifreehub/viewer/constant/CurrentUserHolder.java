package cn.ifreehub.viewer.constant;

public class CurrentUserHolder {
    public static ThreadLocal<String> currentUserName = new ThreadLocal<>();

    /**
     * 设置登录用户名
     *
     * @param userName
     */
    public static void setUserName(String userName) {
        currentUserName.set(userName);

    }

    /**
     * 获取登录用户名
     *
     * @return
     */
    public static String getUserName() {

        return currentUserName.get();
    }

    /**
     * 清除线程用户名,防止线程池回收导致的bug
     */
    public static void clear(){
        currentUserName.remove();
    }

}
