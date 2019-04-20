package cn.ifreehub.viewer;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationListener;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.constant.AppConfig;
import cn.ifreehub.viewer.util.JsonUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

@SpringBootApplication
@ServletComponentScan(basePackages = "cn.ifreehub.viewer.config")
public class FreehubFileViewerApplication {

  private static Logger logger = LoggerFactory.getLogger(FreehubFileViewerApplication.class);

  public static void main(String[] args) {
    SpringApplication application = new SpringApplicationBuilder()
        .sources(FreehubFileViewerApplication.class)
        .build();

    application.run(args);
  }

  }
