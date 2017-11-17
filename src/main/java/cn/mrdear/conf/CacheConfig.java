package cn.mrdear.conf;

import net.sf.ehcache.constructs.web.filter.GzipFilter;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.MultipartConfigElement;

/**
 * 定义一些Filter使用
 * @author Niu Li
 * @date 2016/8/13
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Resource
    private Setting setting;

    /**
     * Ehcache页面缓存
     * @return Ehcache页面缓存过滤器实例
     */
    @Bean
    public FilterRegistrationBean ehcacheFilter(){
        FilterRegistrationBean cacheFilter = new FilterRegistrationBean(new SimplePageCachingFilter());
        Map<String,String> maps = new HashMap<>();
        //设置参数
        maps.put("cacheName","pageCache");
        cacheFilter.setInitParameters(maps);
        //设置路径
        cacheFilter.setUrlPatterns(setting.getCache());
        return cacheFilter;
    }

    /**
     * 据shared与否的设置,
     * Spring分别通过CacheManager.create()
     * 或new CacheManager()方式来创建一个ehcache基地.
     * 也说是说通过这个来设置cache的基地是这里的Spring独用,还是跟别的(如hibernate的Ehcache共享)
     * @return EhCacheManager工厂实例
     */
    @Bean
    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean ();
        cacheManagerFactoryBean.setConfigLocation (new ClassPathResource("ehcache.xml"));
        cacheManagerFactoryBean.setShared(true);
        return cacheManagerFactoryBean;
    }
    /**
     *  ehcache 主要的管理器
     * @param ehCacheManagerFactory EhCacheManager工厂实例
     * @return EhCacheCacheManager缓存管理实例
     */
    @Bean
    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean ehCacheManagerFactory){
        return new EhCacheCacheManager(ehCacheManagerFactory.getObject());
    }

    /**
     * 文件上传临时路径
     * @return MultipartConfig实例
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(setting.getUploadTemppath());
        return factory.createMultipartConfig();
    }
}
