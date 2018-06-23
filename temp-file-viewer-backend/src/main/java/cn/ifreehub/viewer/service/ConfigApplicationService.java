package cn.ifreehub.viewer.service;

import org.springframework.stereotype.Service;

import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.repo.ConfigRepo;

import javax.annotation.Resource;

/**
 * @author Quding Ding
 * @since 2018/6/20
 */
@Service
public class ConfigApplicationService {

  @Resource
  private ConfigRepo configRepo;

  /**
   * 拿到全局配置
   * @return 配置
   */
  public UserConfig getUserConfig() {
    return configRepo.getUserConfig();
  }

}
