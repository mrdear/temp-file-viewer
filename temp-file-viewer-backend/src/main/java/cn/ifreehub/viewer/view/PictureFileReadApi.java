package cn.ifreehub.viewer.view;

import com.google.common.collect.Maps;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.constant.FileType;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.service.ConfigApplicationService;
import cn.ifreehub.viewer.service.FileApplicationService;
import cn.ifreehub.viewer.view.vo.FileDetailVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import javax.annotation.Resource;

/**
 * 图片类型文档写回
 * @author Quding Ding
 * @since 2018/7/20
 */
@RestController
@RequestMapping("api/v1/img/")
public class PictureFileReadApi {

  private static Logger logger = LoggerFactory.getLogger(PictureFileReadApi.class);

  @Resource
  private ConfigApplicationService configApplicationService;
  @Resource
  private FileApplicationService fileApplicationService;
  /**
   *
   * 文件对应的base64前缀
   */
  private static final Map<FileType, String> FILE_BASE64_PREFIX = Maps.newHashMapWithExpectedSize(4);

  static {
    FILE_BASE64_PREFIX.put(FileType.PNG, "data:image/png;base64,");
    FILE_BASE64_PREFIX.put(FileType.JPEG, "data:image/jpeg;base64,");
    FILE_BASE64_PREFIX.put(FileType.JPG, "data:image/jpg;base64,");
    FILE_BASE64_PREFIX.put(FileType.GIF, "data:image/gif;base64,");
  }

  /**
   * 查看图片,返回base64格式
   * @param fileMd5 文件名标识
   * @param passwd 文件密码
   * @return 结果
   */
  @PostMapping("")
  public ApiWrapper showImage(@RequestParam String fileMd5, @RequestParam String passwd) {
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
    // 到这读取文件,转换为base64格式
    try (FileInputStream inputStream = new FileInputStream(new File(reference.getFileAbsolutePath()))) {
      byte[] data = new byte[inputStream.available()];
      inputStream.read(data);
      // 转base64
      String base64Content = Base64.getEncoder().encodeToString(data);
      return ApiWrapper.success(FileDetailVO.newInstance(reference,
          FILE_BASE64_PREFIX.get(reference.getFileType()) + base64Content));

    } catch (IOException e) {
      logger.warn("file can't read,file md5name is {}", fileMd5, e);
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, "文件已过期或已删除");
    }
  }

}
