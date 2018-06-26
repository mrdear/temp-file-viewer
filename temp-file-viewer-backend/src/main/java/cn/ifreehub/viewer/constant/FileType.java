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
  MARKDOWN("md", "/md")
  ;

  public final String identity;
  /**
   * 对应的前端路由地址
   */
  public final String frontRoute;

  FileType(String identity, String frontRoute) {
    this.identity = identity;
    this.frontRoute = frontRoute;
  }

  /**
   * 去除了后缀的文件名
   * @param fullFileName 包含后缀的文件名
   * @return 去除了后缀的文件名
   */
  public String getFileNameWithoutSuffix(String fullFileName) {
    return fullFileName.substring(0, fullFileName.lastIndexOf('.'));
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

  /**
   * 得到后缀
   */
  public String getSuffix() {
    return '.' + this.identity;
  }

}
