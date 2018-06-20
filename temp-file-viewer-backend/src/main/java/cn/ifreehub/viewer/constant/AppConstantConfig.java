package cn.ifreehub.viewer.constant;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
public enum AppConstantConfig implements ConfigKey {
  /**
   * root用户名
   */
  ROOT_USERNAME("${root.username}", String.class),
  /**
   * "root用户密码"
   */
  ROOT_PASSWORD("${root.password}", String.class),
  /**
   * 用户头像地址
   */
  ROOT_AVATAR("${root.avatar}", String.class),
  /**
   * "jwt签名秘钥"
   */
  JWT_SECRET("${jwt.secret}", String.class),
  /**
   * 上传文件目录
   */
  TEMP_FILE_DIST("${temp.file.dist}", String.class);

  public final String key;

  public final Class valueType;

  AppConstantConfig(String key, Class<?> valueType) {
    this.key = key;
    this.valueType = valueType;
  }

  @Override
  public String key() {
    return key;
  }

  @Override
  public Class valueType() {
    return valueType;
  }
}
