package cn.ifreehub.viewer.config;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Quding Ding
 * @since 2018/7/28
 */
@Configuration
public class ThreadPoolConfig {

  /**
   * 处理一些异步任务,单线程池足矣
   */
  @Bean
  public ExecutorService taskExecutor() {
    return new ThreadPoolExecutor(1, 1,
        0L, TimeUnit.MILLISECONDS,
        new LinkedBlockingQueue<>(20),
        new ThreadFactoryBuilder().setNameFormat("temp-file-backtask").build(),
        new ThreadPoolExecutor.CallerRunsPolicy());
  }

}
