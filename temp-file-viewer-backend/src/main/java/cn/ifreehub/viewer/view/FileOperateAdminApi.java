package cn.ifreehub.viewer.view;

import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.constant.CurrentUserHolder;
import cn.ifreehub.viewer.model.User;
import cn.ifreehub.viewer.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;
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

  @Autowired
  private UserRepo userRepo;

  @Value("${temp.file.normalSize:10}")
  private Integer normalSize;

  /**
   * 文件上传接口
   */
  @PostMapping("upload/")
  public ApiWrapper upload(MultipartFile file) {
    if (file.getSize()/1048576 > normalSize){
      User user = userRepo.findUserByUserName(CurrentUserHolder.getUserName());
      if (user!=null && user.getRole() != 0){
        return ApiWrapper.fail(ApiStatus.FILE_TOO_LARGE);
      }
    }
    FileIndexReference reference = FileIndexReference.newInstance(file);
    logger.info("upload file {}", reference);
    UserConfig config = configApplicationService.getUserConfig();

    // 重新上传则不改变密码
    FileIndexReference oldReference = config.getFiles().get(reference.getMd5Name());
    if (null != oldReference) {
      reference.modifyPasswd(oldReference.getPasswd());
    }

    fileApplicationService.addFileIndex(reference, file);

    FileItemVO itemVO = FileItemVO.newInstance(reference);
    return ApiWrapper.success(itemVO);
  }

  /**
   * 删除一个文件
   *
   * @param fileMd5 文件名
   * @return 删除文件
   */
  @PostMapping("delete/")
  public ApiWrapper deleteFile(String fileMd5) {
    Map<String, FileIndexReference> fileMaps = configApplicationService.getFiles();
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

  /**
   * 文件信息更改,因为关系已经建立,因此文件名密码都是允许修改的选项
   * 另外更改文件名并不会重新生成MD5值
   * @param fileMd5 文件标识
   * @param fileName 文件名
   * @param passwd 密码
   */
  @PostMapping("modify/")
  public ApiWrapper modifyPasswd(String fileMd5, String fileName, String passwd) {
    Map<String, FileIndexReference> referenceMap = configApplicationService.getFiles();
    FileIndexReference targetFile = referenceMap.get(fileMd5);
    Assert.notNull(targetFile, "文件不存在");

    targetFile.modifyPasswd(passwd);
    targetFile.modifyFileName(fileName);
    boolean result = fileApplicationService.updateFileReference(targetFile);
    return ApiWrapper.success(result);
  }

}
