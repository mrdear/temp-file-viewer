package cn.ifreehub.viewer.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
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
import cn.ifreehub.viewer.view.valid.FileValidService;
import cn.ifreehub.viewer.view.vo.FileDetailVO;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.annotation.Resource;

/**
 * 文本类型文件返回
 *
 * @author Quding Ding
 * @since 2018/6/18
 */
@RestController
@RequestMapping("api/v1/text/")
public class TextFileReadApi {

  private static Logger logger = LoggerFactory.getLogger(TextFileReadApi.class);

  @Resource
  private ConfigApplicationService configApplicationService;
  @Resource
  private FileApplicationService fileApplicationService;
  @Resource
  private FileValidService fileValidService;

  /**
   * 读取md的内容
   *
   * @param fileMd5 文件标识
   * @param passwd  密码
   * @return 内容
   */
  @PostMapping("md/")
  public ApiWrapper detail(@RequestParam String fileMd5, @RequestParam String passwd) {
    UserConfig config = configApplicationService.getUserConfig();
    FileIndexReference reference = config.getFiles().get(fileMd5);
    try {
      fileValidService.validFile(reference, passwd, "/md");
    } catch (Exception e) {
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, e.getMessage());
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

}
