package cn.ifreehub.viewer.config.mvc;

import com.google.common.collect.ImmutableSet;

import org.springframework.web.filter.OncePerRequestFilter;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.AppConstantConfig;
import cn.ifreehub.viewer.constant.JwtTokenType;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.util.JsonUtils;
import cn.ifreehub.viewer.util.JwtTokenUtils;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 简易账户校验
 * 使用注解WebFilter配置需要配合Spring的Servlet发现机制,需要使用@ServletComponentScan扫描
 * @author Quding Ding
 * @since 2018/6/13
 */
@WebFilter(filterName = "securityFilter", urlPatterns = "/*")
public class SecurityFilter extends OncePerRequestFilter {

  /**
   * 需要权限验证的接口
   */
  private static final Set<String> AUTHOR_URI = ImmutableSet.of("/api/v1/file/upload/",
      "/api/v1/file/delete/","/api/v1/file/modify/","/api/v1/file/list/", "/api/v1/profile/");

  @Override
  protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp,
      FilterChain chain) throws ServletException, IOException {
    String requestURI = req.getRequestURI();
    if (AUTHOR_URI.contains(requestURI)) {
      ApiWrapper<Boolean> apiWrapper = JwtTokenUtils.verifyToken(req);
      if (!apiWrapper.isSuccess()) {
        resp.getWriter().write(JsonUtils.writeString(apiWrapper));
        return;
      }
      // 到这里说明验证成功了,因此判断是否需要刷新token
      if (apiWrapper.getData()) {
        String realUsername = EnvironmentContext.getStringValue(AppConstantConfig.ROOT_USERNAME);
        JwtTokenUtils.create(realUsername, JwtTokenType.DEFAULT, resp);
      }
    }
    chain.doFilter(req, resp);
  }

}
