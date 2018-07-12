package cn.ifreehub.viewer.config.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.domain.ApiWrapper;

import javax.servlet.http.HttpServletRequest;

/**
 * Spring MVC全局异常处理
 * @author Quding Ding
 * @since 2018/6/20
 */
@ControllerAdvice
public class ExceptionResolverConfig {

  private static Logger logger = LoggerFactory.getLogger(ExceptionResolverConfig.class);

  @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class,NullPointerException.class})
  @ResponseBody
  public ApiWrapper paramsException(Exception ex) {
    return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, ex.getMessage());
  }


  /**
   * 上述无法处理时转向的错误,需要记录
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public ApiWrapper exception(Exception ex, HttpServletRequest request) {
    logger.warn("request fail, uri is {}",request.getRequestURI(), ex);
    return ApiWrapper.fail(ApiStatus.FAIL);
  }


}
