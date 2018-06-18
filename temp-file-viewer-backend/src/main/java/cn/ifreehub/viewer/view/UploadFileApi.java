package cn.ifreehub.viewer.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.service.FileApplicationService;
import cn.ifreehub.viewer.view.vo.UploadSuccessVO;

import javax.annotation.Resource;

/**
 * @author Quding Ding
 * @since 2018/6/16
 */
@RestController
@RequestMapping("api/v1/upload/")
public class UploadFileApi {

  private static Logger logger = LoggerFactory.getLogger(UploadFileApi.class);

  @Resource
  private FileApplicationService fileApplicationService;

  /**
   * 文件上传接口
   */
  @PostMapping("")
  public ApiWrapper<UploadSuccessVO> upload(MultipartFile file) {
    FileIndexReference reference = FileIndexReference.newIntance(file);
    logger.info("upload file {}", reference);

    fileApplicationService.addFileIndex(reference, file);

    // vo
    UploadSuccessVO successVO = new UploadSuccessVO()
        .setPasswd(reference.getPasswd())
        .setMd5Name(reference.getMd5Name())
        .setFrontRoute(reference.getFileType().frontRoute);

    return ApiWrapper.success(successVO);
  }


}
