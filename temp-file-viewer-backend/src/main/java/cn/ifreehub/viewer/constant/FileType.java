package cn.ifreehub.viewer.constant;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
public enum FileType {
  /**
   * markdown格式
   */
  MARKDOWN(".md", "/md")
  ;

  public final String sufix;
  /**
   * 对应的前端路由地址
   */
  public final String frontRoute;

  FileType(String sufix, String frontRoute) {
    this.sufix = sufix;
    this.frontRoute = frontRoute;
  }


  public static FileType selectByFile(String fullFileName) {
    Assert.state(StringUtils.isNoneEmpty(fullFileName), "fullFileName can't be null");
    String suffix = fullFileName.substring(fullFileName.lastIndexOf("."));

    switch (suffix.toLowerCase()) {
      case ".md":
      case ".markdown":
        return MARKDOWN;
      default:
        throw new IllegalArgumentException("can't support this file:" + fullFileName);
    }
  }
}
