package cn.ifreehub.viewer.config;

import com.google.common.collect.ImmutableSet;

import org.springframework.web.filter.OncePerRequestFilter;

import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.util.JsonUtils;
import cn.ifreehub.viewer.util.JwtTokenUtils;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 简易账户校验
 * @author Quding Ding
 * @since 2018/6/13
 */
public class SecurityFilter extends OncePerRequestFilter {

  /**
   * 需要权限验证的接口
   */
  private static final Set<String> AUTHOR_URI = ImmutableSet.of("api/v1/upload/","api/v1/profile/");

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
    }
    chain.doFilter(req, resp);
  }

}
