package cn.ifreehub.viewer.view.vo;

import cn.ifreehub.viewer.domain.FileIndexReference;

/**
 * @author Quding Ding
 * @since 2018/6/21
 */
public class FileDetailVO {

  private String fileName;

  private String md5Name;

  private String frontRoute;

  private String passwd;

  private String fileType;
  /**
   * 文件后缀
   */
  private String fileSuffix;

  private String fileContent;

  public static FileDetailVO newInstance(FileIndexReference reference, String fileContent) {
    FileDetailVO detailVO = new FileDetailVO();
    detailVO.setFileName(reference.getFileName());
    detailVO.setMd5Name(reference.getMd5Name());
    detailVO.setFrontRoute(reference.getFileType().frontRoute);
    detailVO.setPasswd(reference.getPasswd());
    detailVO.setFileType(reference.getFileType().identity);
    detailVO.setFileSuffix(reference.getFileType().suffix);
    detailVO.setFileContent(fileContent);
    return detailVO;
  }


  public String getFileName() {
    return fileName;
  }

  public FileDetailVO setFileName(String fileName) {
    this.fileName = fileName;
    return this;
  }

  public String getMd5Name() {
    return md5Name;
  }

  public FileDetailVO setMd5Name(String md5Name) {
    this.md5Name = md5Name;
    return this;
  }

  public String getFrontRoute() {
    return frontRoute;
  }

  public FileDetailVO setFrontRoute(String frontRoute) {
    this.frontRoute = frontRoute;
    return this;
  }

  public String getPasswd() {
    return passwd;
  }

  public FileDetailVO setPasswd(String passwd) {
    this.passwd = passwd;
    return this;
  }

  public String getFileType() {
    return fileType;
  }

  public FileDetailVO setFileType(String fileType) {
    this.fileType = fileType;
    return this;
  }

  public String getFileContent() {
    return fileContent;
  }

  public FileDetailVO setFileContent(String fileContent) {
    this.fileContent = fileContent;
    return this;
  }

  public String getFileSuffix() {
    return fileSuffix;
  }

  public FileDetailVO setFileSuffix(String fileSuffix) {
    this.fileSuffix = fileSuffix;
    return this;
  }
}
