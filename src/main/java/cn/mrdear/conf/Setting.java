package cn.mrdear.conf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 全局配置
 * @author Niu Li
 * @date 2016/11/11
 */
@ConfigurationProperties(prefix = "md",locations = "classpath:setting.properties")
public final class Setting {

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
    private String maxCount = "20";
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


    public String getUploadTemppath() {
        return uploadTemppath;
    }

    public void setUploadTemppath(String uploadTemppath) {
        this.uploadTemppath = uploadTemppath;
    }

    public List<String> getMdpath() {
        return mdpath;
    }

    public void setMdpath(List<String> mdpath) {
        this.mdpath = mdpath;
    }

    public String getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(String maxCount) {
        this.maxCount = maxCount;
    }

    public List<String> getFilter() {
        return filter;
    }

    public void setFilter(List<String> filter) {
        this.filter = filter;
    }

    public List<String> getCache() {
        return cache;
    }

    public void setCache(List<String> cache) {
        this.cache = cache;
    }

    public String getWelecome() {
        return welecome;
    }

    public void setWelecome(String welecome) {
        this.welecome = welecome;
    }
}
