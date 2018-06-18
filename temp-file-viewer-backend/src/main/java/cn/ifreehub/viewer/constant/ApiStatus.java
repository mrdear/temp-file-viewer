package cn.ifreehub.viewer.constant;

/**
 * 全局错误码,以http状态码为参考标准
 * @author Quding Ding
 * @since 2018/5/31
 */
public enum ApiStatus {
  /**
   * 成功标识
   */
  SUCCESS(2000, "success"),
  /**
   * 账户无权限
   */
  NO_AUTHORITY(4001, "未取得认证许可"),
  ;

  public int code;

  public String message;

  ApiStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
