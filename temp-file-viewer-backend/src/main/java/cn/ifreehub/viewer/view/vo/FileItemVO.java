package cn.ifreehub.viewer.view.vo;

import cn.ifreehub.viewer.domain.FileIndexReference;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Quding Ding
 * @since 2018/6/20
 */
public class FileItemVO {

  private String fileName;

  private String md5Name;

  private String frontRoute;

  private String passwd;

  private String fileType;


  public static FileItemVO newInstance(FileIndexReference reference) {
    FileItemVO fileItemVO = new FileItemVO();
    fileItemVO.setFileName(reference.getFileName());
    fileItemVO.setMd5Name(reference.getMd5Name());
    fileItemVO.setFrontRoute(reference.getFileType().frontRoute);
    fileItemVO.setPasswd(reference.getPasswd());
    fileItemVO.setFileType(reference.getFileType().sufix);
    return fileItemVO;
  }

  public static List<FileItemVO> newInstance(List<FileIndexReference> references) {
    return Optional.ofNullable(references)
        .orElse(Collections.emptyList())
        .stream()
        .map(FileItemVO::newInstance)
        .collect(Collectors.toList());
  }


  public String getFileType() {
    return fileType;
  }

  public FileItemVO setFileType(String fileType) {
    this.fileType = fileType;
    return this;
  }

  public String getFileName() {
    return fileName;
  }

  public FileItemVO setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public String getMd5Name() {
    return md5Name;
  }

  public FileItemVO setMd5Name(String md5Name) {
    this.md5Name = md5Name;
    return this;
  }

  public String getFrontRoute() {
    return frontRoute;
  }

  public FileItemVO setFrontRoute(String frontRoute) {
    this.frontRoute = frontRoute;
    return this;
  }

  public String getPasswd() {
    return passwd;
  }

  public FileItemVO setPasswd(String passwd) {
    this.passwd = passwd;
    return this;
  }
}
