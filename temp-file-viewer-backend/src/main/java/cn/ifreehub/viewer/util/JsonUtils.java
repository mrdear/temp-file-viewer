package cn.ifreehub.viewer.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
public class JsonUtils {

  private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

  static {
    // 设定不从get set,直接从对应的属性字段获取
    OBJECTMAPPER.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.NONE);
    OBJECTMAPPER.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
    OBJECTMAPPER.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
  }

  /**
   * 转换任意object到json String
   * @param value 任意object
   * @return json string
   */
  public static String writeString(Object value) {
    try {
      return OBJECTMAPPER.writeValueAsString(value);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * 从文件路径中读取
   * @param path 文件路径
   * @param clazz 类型
   */
  public static <T> T readValueFromPath(String path, Class<T> clazz) {
    try {
      return OBJECTMAPPER.readValue(new File(path), clazz);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
