package cn.ifreehub.viewer.constant;

/**
 * 全局系统配置Key
 * @author Quding Ding
 * @since 2018/6/13
 */
public interface ConfigKey {

  /**
   * 得到存放的key类型
   *
   * @return 该key类型
   */
  String key();

  /**
   * 得到对应的value类型
   *
   * @return value类型
   */
  Class valueType();

}
