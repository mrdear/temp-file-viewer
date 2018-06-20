package cn.ifreehub.viewer.view.vo;

import java.io.Serializable;

/**
 * @author Quding Ding
 * @since 2018/6/19
 */
public class UserProfileVO implements Serializable {

  private String username;

  private String avatar;

  public UserProfileVO(String username, String avatar) {
    this.username = username;
    this.avatar = avatar;
  }

  public String getUsername() {
    return username;
  }

  public String getAvatar() {
    return avatar;
  }
}
