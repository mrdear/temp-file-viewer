package cn.ifreehub.viewer.constant;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
public enum FileType {
  /**
   * 文本类型
   */
  MARKDOWN("md", "markdown", "/md"),
  JAVA("java", "java", "/md"),
  JSON("json", "json", "/md"),
  XML("xml", "xml", "/md"),
  TYPESCRIPT("ts", "typescript", "/md"),
  PHP("php", "php", "/md"),
  C("c", "C", "/md"),
  C_PLUS("cpp", "C++", "/md"),
  JAVASCRIPT("js", "javascript", "/md"),
  PYTHON("py", "python", "/md"),
  CSS("css", "css", "/md"),
  TXT("txt", "txt", "/md"),
  HTML("html", "html", "/md"),
  GO("go", "go", "/md"),

  /**
   * 图片类型
   */
  PNG("png", "png", "/img"),
  JPG("jpg", "jpg", "/img"),
  JPEG("jpeg", "jpeg", "/img"),
  GIF("gif", "gif", "/img"),

  /**
   * office
   */
  DOCX("docx", "docx", "/office"),
  DOC("doc", "doc", "/office"),
  XLSX("xlsx", "xlsx", "/office"),
  XLS("xls", "xls", "/office"),
  PPTX("pptx", "pptx", "/office"),
  PPT("ppt", "ppt", "/office"),
  ;

  private static final Map<String, FileType> ALL_FILE_TYPES = Maps.newHashMap();

  static {
    for (FileType fileType : FileType.values()) {
      ALL_FILE_TYPES.put(fileType.suffix, fileType);
    }
  }

  /**
   * 后缀
   */
  public final String suffix;
  /**
   * 文件标识
   */
  public final String identity;
  /**
   * 对应的前端路由地址
   */
  public final String frontRoute;

  FileType(String suffix, String identity, String frontRoute) {
    this.suffix = suffix;
    this.identity = identity;
    this.frontRoute = frontRoute;
  }

  /**
   * 去除了后缀的文件名
   *
   * @param fullFileName 包含后缀的文件名
   * @return 去除了后缀的文件名
   */
  public String getFileNameWithoutSuffix(String fullFileName) {
    return fullFileName.substring(0, fullFileName.lastIndexOf('.'));
  }

  public static FileType selectByFile(String fullFileName) {
    Assert.state(StringUtils.isNoneEmpty(fullFileName), "fullFileName can't be null");
    String suffix = fullFileName.substring(fullFileName.lastIndexOf(".") + 1);

    FileType fileType = ALL_FILE_TYPES.get(StringUtils.lowerCase(suffix));
    if (null == fileType) {
      throw new IllegalArgumentException("can't support this file:" + fullFileName);
    }
    return fileType;
  }

  /**
   * 得到后缀
   */
  public String getSuffix() {
    return '.' + this.identity;
  }

}
