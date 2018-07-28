package cn.ifreehub.viewer.config;

import io.github.mrdear.tinify.DefaultTinifyClient;
import io.github.mrdear.tinify.TinifyClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import cn.ifreehub.viewer.constant.AppConfig;
import cn.ifreehub.viewer.util.HTTPUtils;

import javax.annotation.Resource;

/**
 * @author Quding Ding
 * @since 2018/7/23
 */
@Configuration
public class TinfyConfig {
  /**
   * 注入保证有依赖关系
   */
  @Resource
  private EnvironmentContext environmentContext;

  @Bean
  public TinifyClient tinifyClient() {
    String apikey = EnvironmentContext.getStringValue(AppConfig.TINYPNG_APIKEY);
    return new DefaultTinifyClient(apikey, new HTTPUtils());
  }

}
