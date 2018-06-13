package cn.ifreehub.viewer.constant;

/**
 * @author Quding Ding
 * @since 2017/10/13
 */
public enum JwtTokenType {
  /**
   * 24小时有效期,小于12小时续期
   */
  DEFAULT(24 * 60 * 60 * 1000L, 12 * 60 * 60 * 1000L);

  /**
   * 过期时间
   */
  public long expireTime;
  /**
   * 续期时间
   */
  public long renewalTime;

  JwtTokenType(long expireTime, long renewalTime) {
    this.expireTime = expireTime;
    this.renewalTime = renewalTime;
  }

}
