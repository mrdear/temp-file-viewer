package cn.mrdear;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import cn.mrdear.conf.Setting;

/**
 * 默认启动类
 */
@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties({Setting.class})
@ComponentScan(basePackages = {"cn.mrdear"})
public class Application{

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}
