package cn.mrdear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import cn.mrdear.conf.Setting;

/**
 * 默认启动类
 */
@SpringBootApplication
@EnableConfigurationProperties({Setting.class})
public class Application {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(Application.class, args);
  }

}
