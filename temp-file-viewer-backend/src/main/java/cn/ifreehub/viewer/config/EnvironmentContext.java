package cn.ifreehub.viewer.config;

import cn.ifreehub.viewer.constant.CurrentUserHolder;
import cn.ifreehub.viewer.util.JsonUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import cn.ifreehub.viewer.constant.AppConfig;
import cn.ifreehub.viewer.constant.ConfigKey;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;


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
   * 得到boolean值
   * @param key 值对应的key
   * @return 结果
   */
  public static boolean getBooleanValue(ConfigKey key) {
    String value = environment.resolvePlaceholders(key.key());
    return Boolean.valueOf(value);
  }

  /**
   * 得到文件夹路径
   * @param key 文件夹配置对应的key
   * @return 文件夹路径
   */
  public static String getFolderPath(ConfigKey key) {
    String value = getStringValue(key);
    String suffix = CurrentUserHolder.getUserName() + File.separator;
    if (value.endsWith(File.separator)) {
      return value + suffix;
    }
    return value + File.separator +suffix;
  }

  /**
   * 得到配置文件路径
   * @return 配置文件
   */
  public static String getConfigFilePath() {
    String distPath = EnvironmentContext.getFolderPath(AppConfig.TEMP_FILE_DIST);
    return distPath + "config" + File.separator + "config.json";
  }

  public static void createUserConfig() {

    String configFilePath = EnvironmentContext.getConfigFilePath();

    File file = new File(configFilePath);
    if (!file.exists()) {
      try {
        // 创建文件
        FileUtils.forceMkdirParent(file);
        FileWriter writer = new FileWriter(file);

        writer.write(JsonUtils.writeString(new cn.ifreehub.viewer.domain.UserConfig()
                .setFiles(Collections.emptyMap())));
        writer.flush();
        writer.close();

        // 修改权限,禁止执行权限

        String distPath = EnvironmentContext.getFolderPath(AppConfig.TEMP_FILE_DIST);
        File distFile = new File(distPath);
        distFile.setExecutable(true, false);
      } catch (IOException e) {
        e.printStackTrace();
        throw new Error("config创建失败");
      }
    }
  }
}
