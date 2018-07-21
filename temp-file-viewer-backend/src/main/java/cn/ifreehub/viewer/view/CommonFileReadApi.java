package cn.ifreehub.viewer.view;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.service.ConfigApplicationService;
import cn.ifreehub.viewer.service.FileApplicationService;

import javax.annotation.Resource;

/**
 * 通用文件操作API
 * @author Quding Ding
 * @since 2018/7/20
 */
@RestController
@RequestMapping("api/v1/")
public class CommonFileReadApi {

  @Resource
  private ConfigApplicationService configApplicationService;
  @Resource
  private FileApplicationService fileApplicationService;

  /**
   * 密码检查
   */
  @GetMapping("check/passwd/")
  public ApiWrapper checkPass(@RequestParam String fileMd5, @RequestParam String passwd) {
    UserConfig config = configApplicationService.getUserConfig();
    FileIndexReference reference = config.getFiles().get(fileMd5);

    // 文件过期则删除
    if (!reference.valid()) {
      fileApplicationService.removeFileIndex(reference);
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, "文件已过期或已删除");
    }

    // 密码校验
    if (!StringUtils.equals(passwd, reference.getPasswd())) {
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, "密码错误");
    }
    return ApiWrapper.success();
  }

}
