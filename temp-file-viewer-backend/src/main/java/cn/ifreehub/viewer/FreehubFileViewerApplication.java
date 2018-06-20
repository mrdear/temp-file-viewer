package cn.ifreehub.viewer;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.util.JsonUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;

@SpringBootApplication
public class FreehubFileViewerApplication {

  private static Logger logger = LoggerFactory.getLogger(FreehubFileViewerApplication.class);

  public static void main(String[] args) {
    SpringApplication application = new SpringApplicationBuilder()
        .sources(FreehubFileViewerApplication.class)
        // 启动监听事件
        .listeners(new AppInitListener())
        .build();

    application.run(args);
  }


  /**
   * 容器准备准备后执行的操作
   */
  private static class AppInitListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
      logger.info("AppInitListener start exec");

      String configFilePath = EnvironmentContext.getConfigFilePath();

      File file = new File(configFilePath);
      if (!file.exists()) {

        try (FileWriter writer = new FileWriter(file)) {
          FileUtils.forceMkdirParent(file);
          writer.write(JsonUtils.writeString(new cn.ifreehub.viewer.domain.UserConfig()
              .setFiles(Collections.emptyMap())));
          writer.flush();
          logger.info("file config path create success");

        } catch (IOException e) {
          throw new Error("config创建失败");
        }
      }
    }
  }

}