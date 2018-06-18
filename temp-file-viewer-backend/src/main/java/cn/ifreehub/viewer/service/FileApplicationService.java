package cn.ifreehub.viewer.service;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import cn.ifreehub.viewer.config.EnvironmentContext;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.util.JsonUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 领域对应的服务
 *
 * @author Quding Ding
 * @since 2018/6/17
 */
@Service
public class FileApplicationService {

  private static Logger logger = LoggerFactory.getLogger(FileApplicationService.class);
  /**
   * 控制配置更新的读写锁
   */
  private static final ReadWriteLock configLock = new ReentrantReadWriteLock();

  /**
   * 添加一个文件
   *
   * @param reference 要添加的文件信息
   */
  public void addFileIndex(FileIndexReference reference, MultipartFile file) {
    // 保存config
    if (saveConfig(reference)) {
      // 保存文件
      try {
        file.transferTo(new File(reference.getFileAbsolutePath()));
      } catch (IOException e) {
        logger.error("save file fail, file is {}", reference, e);
      }
    }
  }

  /**
   * 获取全部文件
   *
   * @return 全部文件
   */
  public UserConfig getUserConfig() {
    Lock readLock = configLock.readLock();
    readLock.lock();
    try {
      String configPath = EnvironmentContext.getConfigFilePath();
      return JsonUtils.readValueFromPath(configPath, UserConfig.class);
    } finally {
      readLock.unlock();
    }
  }

  /**
   * 保存配置
   *
   * @return true 成功
   */
  private boolean saveConfig(FileIndexReference reference) {
    Lock writeLock = configLock.writeLock();
    writeLock.lock();
    try {
      // 添加新文件
      UserConfig userConfig = getUserConfig();
      if (null == userConfig.getFiles() || userConfig.getFiles().isEmpty()) {
        userConfig.setFiles(Maps.newHashMapWithExpectedSize(2));
      }
      userConfig.getFiles().put(reference.getMd5Name(), reference);

      String configFilePath = EnvironmentContext.getConfigFilePath();
      try (FileWriter out = new FileWriter(new File(configFilePath))) {
        FileCopyUtils.copy(JsonUtils.writeString(userConfig), out);
        return true;
      } catch (IOException e) {
        logger.error("save config fail", e);
      }
      return false;
    } finally {
      writeLock.unlock();
    }
  }

}
