package cn.mrdear.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * 读取properties文件的工具类
 */
public class PropertiesUtil {

    private static Properties properties;
    private static String url;
    static {
        properties = new Properties();
        try {
           InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream("config.properties");
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取属性
     * @param key
     * @return
     */
    public static String getProperty(String key){
       return properties.getProperty(key);
    }

    /**
     * 设置属性
     * @param key
     * @param value
     */
    public static void setProperty(String key,String value) throws IOException {
        OutputStream out = new FileOutputStream(url);
        properties.setProperty(key,value);
        properties.store(out,"『comments』Update key：" + key);
    }

//    public static void main(String[] args) {
//        String path = getProperty("md.path");
//        System.out.println(path);
//    }
}
