package cn.mrdear.conf;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

/**
 * 全局配置
 * @author Niu Li
 * @since 2016/11/11
 */
@Configuration
@ConfigurationProperties(prefix = "md")
@PropertySource(value = "classpath:setting.properties")
@Data
public class Setting {

    /**
     * 上传文件临时目录
     */
    private String uploadTemppath = "/home/web_as/markdown/logs/";
    /**
     * markdown目录
     */
    private List<String> mdpath;
    /**
     * markdown目录最大获取数量
     */
    private Integer maxCount = 20;
    /**
     * GZIP过滤后缀集合
     */
    private List<String> filter;
    /**
     * 使用页面缓存的地址
     */
    private List<String> cache;
    /**
     * 首页欢迎语
     */
    private String welecome = "#欢迎使用Markdown View Tools";

}
