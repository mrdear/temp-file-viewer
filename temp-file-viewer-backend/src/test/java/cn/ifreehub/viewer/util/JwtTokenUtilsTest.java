package cn.ifreehub.viewer.util;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.JwtTokenType;
import cn.ifreehub.viewer.domain.ApiWrapper;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@RunWith(MockitoJUnitRunner.class)
public class JwtTokenUtilsTest {

  @Mock
  private Environment environment;
  @Mock
  private HttpServletRequest request;

  @Before
  public void before() {
    EnvironmentContext context = new EnvironmentContext();
    context.setEnvironment(environment);
  }

  @Test
  public void verifyToken() {
    Mockito.when(environment.resolvePlaceholders(Mockito.any()))
        .thenReturn("123456");
    String token = JwtTokenUtils.create("张三", JwtTokenType.DEFAULT);

    Cookie cookie = new Cookie("file_viewer_auth", token);
    Mockito.when(request.getCookies())
        .thenReturn(new Cookie[]{cookie});

    ApiWrapper<Boolean> wrapper = JwtTokenUtils.verifyToken(request);
    Assert.assertTrue(wrapper.isSuccess() && wrapper.getData());
  }

  @Test
  public void create() {
    Mockito.when(environment.resolvePlaceholders(Mockito.any()))
        .thenReturn("123456");
    JwtTokenUtils.create("张三", JwtTokenType.DEFAULT);
  }
}