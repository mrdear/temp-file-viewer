package cn.ifreehub.viewer.constant;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
public enum AppConstantConfig implements ConfigKey {

  ROOT_USERNAME("${root.username}", String.class, "root用户名"),
  ROOT_PASSWORD("${root.password}", String.class, "root用户密码"),
  JWT_SECRET("${jwt_secret}", String.class, "jwt签名秘钥"),
  ;

  public final String key;

  public final Class valueType;

  AppConstantConfig(String key, Class<?> valueType, String desc) {
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
