package cn.ifreehub.viewer.util;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.constant.AppConstantConfig;
import cn.ifreehub.viewer.constant.JwtTokenType;
import cn.ifreehub.viewer.domain.ApiWrapper;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Quding Ding
 * @since 2017/10/11
 */
public class JwtTokenUtils {

  private static final String FILE_VIEWER_AUTH = "file_viewer_auth";
  private static Logger logger = LoggerFactory.getLogger(JwtTokenUtils.class);

  /**
   * 验证token
   *
   * @param request 请求
   * @return 包装结果, 只有status为success时可用
   */
  public static ApiWrapper<Boolean> verifyToken(HttpServletRequest request) {
    // 获取token
    String token = null;
    Cookie[] cookies = request.getCookies();
    if (null != cookies && cookies.length > 0) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(FILE_VIEWER_AUTH)) {
          token = cookie.getValue();
          break;
        }
      }
    }
    if (StringUtils.isEmpty(token)) {
      return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
    }
    // 检验token
    try {
      Jwts.parser()
          .setSigningKey(getSecret())
          .parseClaimsJws(token)
          .getBody();
    } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException e) {
      return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
    } catch (Exception e) {
      logger.error("parse token fail,token is {}", token, e);
      return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
    }
    return ApiWrapper.success(true);
  }

  /**
   * 创建token并写入到cookie中
   * @param response 响应流
   */
  public static void create(String username, String password, JwtTokenType tokenType, HttpServletResponse response) {
    String token = create(username, password, tokenType);
    Cookie cookie = new Cookie(FILE_VIEWER_AUTH, token);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(Math.toIntExact(tokenType.expireTime / 1000));
    response.addCookie(cookie);
  }

  /**
   * 生成一个jwtToken
   *
   * @param username  登录用户名
   * @param password  登录用户密码
   * @param tokenType token类型
   * @return token
   */
  public static String create(String username, String password, JwtTokenType tokenType) {
    return Jwts.builder()
        .setExpiration(new Date(System.currentTimeMillis() + tokenType.expireTime))
        .setSubject(username)
        //先占坑
        .claim("version", 1)
        .signWith(SignatureAlgorithm.HS256, getSecret())
        .compact();
  }

  private static String getSecret() {
    return EnvironmentContext.getStringValue(AppConstantConfig.JWT_SECRET);
  }


  public static String urlEncode(String str) {
    if (StringUtils.isBlank(str)) {
      return StringUtils.EMPTY;
    }
    try {
      return URLEncoder.encode(str, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      // do nothing
    }
    return StringUtils.EMPTY;
  }

}
