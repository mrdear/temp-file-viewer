package cn.ifreehub.viewer.config;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.ifreehub.viewer.constant.AppConstantConfig;
import cn.ifreehub.viewer.constant.ConfigKey;

import java.io.File;


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

  /**
   * 得到文件夹路径
   * @param key 文件夹配置对应的key
   * @return 文件夹路径
   */
  public static String getFolderPath(ConfigKey key) {
    String value = getStringValue(key);
    if (value.endsWith(File.separator)) {
      return value;
    }
    return value + File.separator;
  }

  /**
   * 得到配置文件路径
   * @return 配置文件
   */
  public static String getConfigFilePath() {
    String distPath = EnvironmentContext.getFolderPath(AppConstantConfig.TEMP_FILE_DIST);
    return distPath + "config" + File.separator + "config.json";
  }
}
