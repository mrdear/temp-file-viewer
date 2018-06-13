package cn.ifreehub.viewer.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.ifreehub.viewer.constant.ConfigKey;


/**
 * @author Quding Ding
 * @since 2018/5/31
 */
@Component
public class EnvironmentContext implements EnvironmentAware, InitializingBean {

  private static Environment environment;

  @Override
  public void setEnvironment(Environment environment) {
    EnvironmentContext.environment = environment;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    Assert.notNull(environment, "Spring环境注入失败");
  }

  /**
   * 得到对应key的value值
   *
   * @param key key
   * @return String类型值
   */
  public static String getStringValue(ConfigKey key) {
    return environment.resolvePlaceholders(key.key());
  }


}
