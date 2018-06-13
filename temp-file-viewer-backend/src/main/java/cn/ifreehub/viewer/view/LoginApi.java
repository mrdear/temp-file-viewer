package cn.ifreehub.viewer.view;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.constant.AppConstantConfig;
import cn.ifreehub.viewer.constant.JwtTokenType;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.util.JwtTokenUtils;

import java.util.Objects;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Quding Ding
 * @since 2018/6/13
 */
@RestController
@RequestMapping("api/v1/login/")
public class LoginApi {

  /**
   * 简易登录接口
   * @param username 用户名
   * @param passwdsha 用户密码sha256结果
   */
  @PostMapping("")
  public ApiWrapper login(String username, String passwdsha, HttpServletResponse response) {
    String realUsername = EnvironmentContext.getStringValue(AppConstantConfig.ROOT_USERNAME);
    if (!Objects.equals(username, realUsername)) {
      return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
    }
    String realPwd = EnvironmentContext.getStringValue(AppConstantConfig.ROOT_PASSWORD);
    if (!Objects.equals(new String(DigestUtils.sha256(realPwd)), passwdsha)) {
      return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
    }
    // 登录成功,下发token
    JwtTokenUtils.create(realUsername, realPwd, JwtTokenType.DEFAULT, response);
    return ApiWrapper.success();
  }

}
