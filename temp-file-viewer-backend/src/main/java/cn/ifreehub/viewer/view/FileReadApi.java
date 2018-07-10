package cn.ifreehub.viewer.view;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.service.ConfigApplicationService;
import cn.ifreehub.viewer.service.FileApplicationService;
import cn.ifreehub.viewer.view.vo.FileDetailVO;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.Resource;

/**
 * 文件跳转
 *
 * @author Quding Ding
 * @since 2018/6/18
 */
@RestController
@RequestMapping("api/v1/")
public class FileReadApi {

  private static Logger logger = LoggerFactory.getLogger(FileReadApi.class);

  @Resource
  private ConfigApplicationService configApplicationService;
  @Resource
  private FileApplicationService fileApplicationService;

  /**
   * 读取md的内容
   * @param fileMd5 文件标识
   * @param passwd 密码
   * @return 内容
   */
  @PostMapping("md/")
  public ApiWrapper detail(@RequestParam String fileMd5, @RequestParam String passwd) {
    UserConfig config = configApplicationService.getUserConfig();
    FileIndexReference reference = config.getFiles().get(fileMd5);

    if (null == reference) {
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, "文件已过期或已删除");
    }

    // 文件过期则删除
    if (!reference.valid()) {
      fileApplicationService.removeFileIndex(reference);
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, "文件已过期或已删除");
    }

    if (!StringUtils.equals(passwd, reference.getPasswd())) {
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, "密码错误");
    }

    // 读取文件
    try (FileReader reader = new FileReader(new File(reference.getFileAbsolutePath()))) {

      String content = FileCopyUtils.copyToString(reader);
      return ApiWrapper.success(FileDetailVO.newInstance(reference, content));

    } catch (IOException e) {
      logger.warn("file can't read,file md5name is {}", fileMd5, e);
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, "文件已过期或已删除");
    }
  }

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
