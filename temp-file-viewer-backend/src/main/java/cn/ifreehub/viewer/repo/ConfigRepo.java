package cn.ifreehub.viewer.repo;

import com.google.common.collect.Maps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

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
 * 配置仓储操作,对配置的所有操作都要在之类进行.
 * 写入原则,只写入变化的部分,避免更新被覆盖.
 * @author Quding Ding
 * @since 2018/6/20
 */
@Repository
public class ConfigRepo {

  private static Logger logger = LoggerFactory.getLogger(ConfigRepo.class);
  /**
   * 控制配置更新的读写锁,由使用方控制
   */
  private static final ReadWriteLock CONFIG_LOCK = new ReentrantReadWriteLock();

  /**
   * 获取全部文件
   *
   * @return 全部文件
   */
  public UserConfig getUserConfig() {
    Lock readLock = ConfigRepo.CONFIG_LOCK.readLock();
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
  public boolean addFileIndexConfig(FileIndexReference reference) {
    Lock writeLock = ConfigRepo.CONFIG_LOCK.writeLock();
    writeLock.lock();
    try {
      // 添加新文件
      UserConfig userConfig = getUserConfig();
      if (null == userConfig.getFiles() || userConfig.getFiles().isEmpty()) {
        userConfig.setFiles(Maps.newHashMapWithExpectedSize(2));
      }
      userConfig.getFiles().put(reference.getMd5Name(), reference);
      return saveConfig(userConfig);
    } finally {
      writeLock.unlock();
    }
  }

  /**
   * 删除指定文件,以及文件对应的磁盘数据
   * @param reference 文件索引
   * @return true删除成功
   */
  public boolean removeFileIndex(FileIndexReference reference) {
    Lock writeLock = ConfigRepo.CONFIG_LOCK.writeLock();
    writeLock.lock();
    try {
      UserConfig userConfig = getUserConfig();
      userConfig.getFiles().remove(reference.getMd5Name());
      return saveConfig(userConfig);
    } finally {
      writeLock.unlock();
    }
  }


  /**
   * 保存用户配置
   * @param userConfig 用户配置
   * @return true保存成功
   */
  private boolean saveConfig(UserConfig userConfig) {
    String configFilePath = EnvironmentContext.getConfigFilePath();
    try (FileWriter out = new FileWriter(new File(configFilePath))) {
      FileCopyUtils.copy(JsonUtils.writeString(userConfig), out);
      return true;
    } catch (IOException e) {
      logger.error("save config fail", e);
    }
    return false;
  }

}
