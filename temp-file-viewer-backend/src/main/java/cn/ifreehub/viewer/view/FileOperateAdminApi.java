package cn.ifreehub.viewer.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.service.ConfigApplicationService;
import cn.ifreehub.viewer.service.FileApplicationService;
import cn.ifreehub.viewer.view.vo.FileItemVO;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

/**
 * @author Quding Ding
 * @since 2018/6/16
 */
@RestController
@RequestMapping("api/v1/file/")
public class FileOperateAdminApi {

  private static Logger logger = LoggerFactory.getLogger(FileOperateAdminApi.class);

  @Resource
  private FileApplicationService fileApplicationService;
  @Resource
  private ConfigApplicationService configApplicationService;

  /**
   * 文件上传接口
   */
  @PostMapping("upload/")
  public ApiWrapper upload(MultipartFile file) {
    FileIndexReference reference = FileIndexReference.newIntance(file);
    logger.info("upload file {}", reference);

    fileApplicationService.addFileIndex(reference, file);

    FileItemVO itemVO = FileItemVO.newInstance(reference);
    return ApiWrapper.success(itemVO);
  }

  /**
   * 删除一个文件
   * @param fileMd5 文件名
   * @return 删除文件
   */
  @PostMapping("delete/")
  public ApiWrapper deleteFile(String fileMd5) {
    UserConfig userConfig = configApplicationService.getUserConfig();
    Map<String, FileIndexReference> fileMaps = userConfig.getFiles();
    fileApplicationService.removeFileIndex(fileMaps.get(fileMd5));
    return ApiWrapper.success();
  }

  /**
   * 列出文件列表
   */
  @GetMapping("list/")
  public ApiWrapper listFile() {
    List<FileIndexReference> references = fileApplicationService.queryAllFile();
    return ApiWrapper.success(FileItemVO.newInstance(references));
  }

}
