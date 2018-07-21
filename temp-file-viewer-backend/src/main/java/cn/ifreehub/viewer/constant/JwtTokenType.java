package cn.ifreehub.viewer.constant;

import java.util.Date;

/**
 * @author Quding Ding
 * @since 2017/10/13
 */
public enum JwtTokenType {
  /**
   * 7 * 24小时有效期,小于24小时续期
   */
  DEFAULT(7 * 24 * 60 * 60 * 1000L, 24 * 60 * 60 * 1000L);

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

  /**
   * 是否需要刷新
   */
  public boolean needRenewal(Date expireDate) {
    if (null == expireDate) {
      return false;
    }
    long expireTime = expireDate.getTime();
    return expireTime - System.currentTimeMillis() < this.renewalTime;
  }

}
