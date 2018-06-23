package cn.ifreehub.viewer.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * 短分享密码生成工具
 * @author Quding Ding
 * @since 2018/6/16
 */
public class ShortPasswdUtil {

  private static final char[] LETTERS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

  /**
   * 根据md5值生成文件的访问密码
   * @param md5Name 文件名 暂时没用到,后面改算法可能用得到
   * @return 访问密码
   */
  public static String generateShareKey(String md5Name) {
    StringBuilder builder = new StringBuilder(4);
    ThreadLocalRandom current = ThreadLocalRandom.current();
    for (int i = 0; i < 4; i++) {
      builder.append(LETTERS[current.nextInt(LETTERS.length)]);
    }
    return builder.toString();
  }

}
