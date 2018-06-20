package cn.ifreehub.viewer.config.mvc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.util.JsonUtils;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
  public void paramsException(Exception ex,HttpServletResponse response) throws IOException {
    ApiWrapper<Object> fail = ApiWrapper.fail(ApiStatus.PARAMS_ERROR, ex.getMessage());
    response.sendError(HttpStatus.BAD_REQUEST.value(), JsonUtils.writeString(fail));
  }

  /**
   * 上述无法处理时转向的错误,需要记录
   */
  @ExceptionHandler(value = Exception.class)
  @ResponseBody
  public void exception(Exception ex, HttpServletRequest request,HttpServletResponse response) throws IOException {
    logger.warn("request fail, uri is {}",request.getRequestURI(), ex);
    ApiWrapper<Object> apiWrapper = ApiWrapper.fail(ApiStatus.FAIL);
    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), JsonUtils.writeString(apiWrapper));
  }

}
