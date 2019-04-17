package cn.ifreehub.viewer.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class MultipartConfig {
    public static final String WINDOWS_TEMP_UPLOAD_PATH = "D:\\tmp\\tomcat-upload\\";
    public static final String LINUX_TEMP_UPLOAD_PATH = "/tmp/tomcat-upload/";
    public static final String MAC_OS_TEMP_UPLOAD_PATH = "/tmp/tomcat-upload/";

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        String tmpUtl = "";
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            tmpUtl = WINDOWS_TEMP_UPLOAD_PATH;
        } else if (osName.contains("Mac OS X")) {
            tmpUtl = MAC_OS_TEMP_UPLOAD_PATH;
            System.out.println("请自行定义MAC Os的上传临时路径");
        } else if (osName.contains("Linux")) {
            tmpUtl = LINUX_TEMP_UPLOAD_PATH;
        }
        File tmp = new File(tmpUtl);
        if (!tmp.exists()) {
            tmp.mkdirs();
        }
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(tmpUtl);
        return factory.createMultipartConfig();
    }

}
