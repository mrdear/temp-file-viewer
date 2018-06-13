package cn.ifreehub.viewer.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
public class JsonUtils {

  private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

  private static final ObjectMapper OBJECTMAPPER = new ObjectMapper();

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

}
