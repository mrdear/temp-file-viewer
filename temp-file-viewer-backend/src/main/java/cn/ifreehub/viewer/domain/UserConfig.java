package cn.ifreehub.viewer.domain;

import java.util.Map;

/**
 * 保存用户配置
 * @author Quding Ding
 * @since 2018/6/17
 */
public class UserConfig {

  private Map<String, FileIndexReference> files;


  public Map<String, FileIndexReference> getFiles() {
    return files;
  }

  public UserConfig setFiles(Map<String, FileIndexReference> files) {
    this.files = files;
    return this;
  }
}
