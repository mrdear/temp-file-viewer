package cn.ifreehub.viewer.view.vo;

/**
 * 上传成功对应的VO
 * @author Quding Ding
 * @since 2018/6/17
 */
public class UploadSuccessVO {

  private String frontRoute;

  private String md5Name;

  private String passwd;

  public String getFrontRoute() {
    return frontRoute;
  }

  public UploadSuccessVO setFrontRoute(String frontRoute) {
    this.frontRoute = frontRoute;
    return this;
  }

  public String getMd5Name() {
    return md5Name;
  }

  public UploadSuccessVO setMd5Name(String md5Name) {
    this.md5Name = md5Name;
    return this;
  }

  public String getPasswd() {
    return passwd;
  }

  public UploadSuccessVO setPasswd(String passwd) {
    this.passwd = passwd;
    return this;
  }
}
