package cn.ifreehub.viewer.view;

import com.google.common.collect.Maps;

import org.apache.commons.codec.Charsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ifreehub.viewer.constant.ApiStatus;
import cn.ifreehub.viewer.constant.FileType;
import cn.ifreehub.viewer.domain.ApiWrapper;
import cn.ifreehub.viewer.domain.FileIndexReference;
import cn.ifreehub.viewer.domain.UserConfig;
import cn.ifreehub.viewer.service.ConfigApplicationService;
import cn.ifreehub.viewer.service.FileApplicationService;
import cn.ifreehub.viewer.view.valid.FileValidService;
import cn.ifreehub.viewer.view.vo.FileDetailVO;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文档类型文件
 *
 * @author Quding Ding
 * @since 2018/7/30
 */
@Controller
@RequestMapping("api/v1/office/")
public class OfficeFileReadApi {

  private static Logger logger = LoggerFactory.getLogger(OfficeFileReadApi.class);
  @Resource
  private ConfigApplicationService configApplicationService;
  @Resource
  private FileApplicationService fileApplicationService;
  @Resource
  private FileValidService fileValidService;
  /**
   *
   * 文件返回content type值
   */
  private static final Map<FileType, String> OFFICE_CONTENT_TYPE = Maps.newHashMapWithExpectedSize(4);

  static {
    OFFICE_CONTENT_TYPE.put(FileType.XLSX, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    OFFICE_CONTENT_TYPE.put(FileType.XLS, "application/vnd.ms-excel");
    OFFICE_CONTENT_TYPE.put(FileType.DOCX, "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    OFFICE_CONTENT_TYPE.put(FileType.DOC, "application/msword");
    OFFICE_CONTENT_TYPE.put(FileType.PPTX, "application/vnd.openxmlformats-officedocument.presentationml.presentation");
    OFFICE_CONTENT_TYPE.put(FileType.PPT, "application/vnd.ms-powerpoint");
  }

  /**
   * 文档展示信息
   */
  @PostMapping("info/")
  @ResponseBody
  public ApiWrapper office(@RequestParam String fileMd5, @RequestParam String passwd, HttpServletRequest request) {
    UserConfig config = configApplicationService.getUserConfig();
    FileIndexReference reference = config.getFiles().get(fileMd5);

    try {
      fileValidService.validFile(reference, passwd, "/office");
    } catch (Exception e) {
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR, e.getMessage());
    }

    // 构造返回的地址
    return ApiWrapper.success(FileDetailVO.newInstance(reference, "/" + fileMd5 + "/" + passwd + "/"));
  }

  /**
   * 得到具体的文件
   */
  @CrossOrigin
  @GetMapping("{file}/{passwd}/")
  public ApiWrapper showOffice(@PathVariable("file") String fileMd5,
      @PathVariable("passwd") String passwd,
      HttpServletResponse response) {
    UserConfig config = configApplicationService.getUserConfig();
    FileIndexReference reference = config.getFiles().get(fileMd5);

    try {
      fileValidService.validFile(reference, passwd, "/office");
    } catch (Exception e) {
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR);
    }

    // 返回文件流
    response.setContentType(OFFICE_CONTENT_TYPE.getOrDefault(reference.getFileType(), "application/octet-stream"));
    response.setCharacterEncoding(Charsets.UTF_8.name());
    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Expose-Headers",
        "x-ms-request-id,Server,x-ms-version,Content-Type,Last-Modified,ETag,x-ms-lease-status,x-ms-blob-type,Accept-Ranges,Content-Length,Date,Transfer-Encoding");
    response.setHeader("Content-Disposition", "attachment; filename=" + reference.getFileName() + reference.getFileType().getSuffix());
    try {
      FileCopyUtils.copy(new FileInputStream(new File(reference.getFileAbsolutePath())), response.getOutputStream());
      response.flushBuffer();
    } catch (IOException e) {
      logger.error("showOffice fail", e);
      return ApiWrapper.fail(ApiStatus.PARAMS_ERROR);
    }
    return ApiWrapper.success();
  }

}
