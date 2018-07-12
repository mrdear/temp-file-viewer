package cn.ifreehub.viewer.util;


import io.jsonwebtoken.Claims;
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

import java.util.Date;
import java.util.UUID;

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
   * @return 包装结果, 只有status为success时可用,另外data标识是否需要刷新token
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
      Claims claims = Jwts.parser()
          .setSigningKey(getSecret())
          .parseClaimsJws(token)
          .getBody();
      JwtTokenType tokenType = JwtTokenType.valueOf(String.valueOf(claims.get("type")));
      if (tokenType.needRenewal(claims.getExpiration())) {
        return ApiWrapper.success(true);
      }
    } catch (UnsupportedJwtException | MalformedJwtException | SignatureException | ExpiredJwtException e) {
      return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
    } catch (Exception e) {
      logger.error("parse token fail,token is {}", token, e);
      return ApiWrapper.fail(ApiStatus.NO_AUTHORITY);
    }
    return ApiWrapper.success(false);
  }

  /**
   * 创建token并写入到cookie中
   * @param response 响应流
   */
  public static void create(String username, JwtTokenType tokenType, HttpServletResponse response) {
    String token = create(username, tokenType);
    Cookie cookie = new Cookie(FILE_VIEWER_AUTH, token);
    cookie.setHttpOnly(true);

    String hostname = EnvironmentContext.getStringValue(AppConstantConfig.TEMP_HOSTNAME);
    if (!"dev".equals(EnvironmentContext.getStringValue(AppConstantConfig.SPRING_PROFILES_ACTIVE))
        && StringUtils.isNotEmpty(hostname)) {
      cookie.setDomain(hostname);
    }

    cookie.setMaxAge(Math.toIntExact(tokenType.expireTime / 1000));
    cookie.setPath("/");
    response.addCookie(cookie);
  }

  /**
   * 生成一个jwtToken
   *
   * @param username  登录用户名
   * @param tokenType token类型
   * @return token
   */
  public static String create(String username, JwtTokenType tokenType) {
    return Jwts.builder()
        .setExpiration(new Date(System.currentTimeMillis() + tokenType.expireTime))
        .setSubject(username)
        //先占坑
        .claim("version", 1)
        .claim("type", tokenType.name())
        .signWith(SignatureAlgorithm.HS256, getSecret())
        .compact();
  }

  /**
   * 秘钥大多时候不需要配置,每次重启后自动生成新的,保证root的安全
   */
  private static final String TEMP_SECRET = UUID.randomUUID().toString();

  private static String getSecret() {
    String value = EnvironmentContext.getStringValue(AppConstantConfig.JWT_SECRET);
    if (StringUtils.isEmpty(value) || StringUtils.equals(value, AppConstantConfig.JWT_SECRET.key)) {
      value = TEMP_SECRET;
    }
    return value;
  }


}
