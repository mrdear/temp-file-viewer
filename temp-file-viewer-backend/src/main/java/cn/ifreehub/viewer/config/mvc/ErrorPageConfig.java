package cn.ifreehub.viewer.config.mvc;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * @author Quding Ding
 * @since 2018/7/12
 */
@Configuration
public class ErrorPageConfig {

  /**
   * 针对单页应用404请求转向对应的index页面
   */
  @Bean
  public ErrorPageRegistrar errorPageRegistrar(){
    return registry -> {
      registry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,"/index.html"));
    };
  }

}
