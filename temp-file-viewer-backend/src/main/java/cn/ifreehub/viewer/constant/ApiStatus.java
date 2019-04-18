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
  /**
   * 参数错误
   */
  PARAMS_ERROR(4002, "参数错误"),

  FAIL(5001, "服务端错误"),

  FILE_TOO_LARGE(4003,"文件太大"),

  USER_ALREADY_EXISTS(4005,"要注册的用户已存在")
  ;

  public int code;

  public String message;

  ApiStatus(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
