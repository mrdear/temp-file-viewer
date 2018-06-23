package cn.ifreehub.viewer.util;

import org.junit.Assert;
import org.junit.Test;

public class JsonUtilsTest {

  @Test
  public void writeString() {
    User user = new User("张三", "123456");
    String userJson = JsonUtils.writeString(user);
    Assert.assertEquals(userJson, "{\"username\":\"张三\",\"password\":\"123456\"}");
  }

  @Test
  public void readValueFromPath() {
    String path = JsonUtilsTest.class.getClassLoader().getResource("").getPath() + "user.json";
    User user = JsonUtils.readValueFromPath(path, User.class);
    Assert.assertEquals(user.getUsername(), "张三");
  }


  private static class User{
    private String username;

    private String password;

    public User() {
    }

    public User(String username, String password) {
      this.username = username;
      this.password = password;
    }

    public String getUsername() {
      return username;
    }

    public String getPassword() {
      return password;
    }
  }


}