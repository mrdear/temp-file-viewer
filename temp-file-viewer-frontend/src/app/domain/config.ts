/**
 * 全站配置
 */
export class Config {

  base_url: string = '';

  /**
   * 登录请求
   */
  loginUrl: string = `/api/v1/login/`;
  /**
   * 用户信息
   */
  profileUrl: string = `/api/v1/profile/`;
  /**
   * 得到上传文件列表
   */
  allFilesUrl: string = `/api/v1/file/list/`;
  /**
   * 删除文件
   */
  deleteFilesUrl: string = `/api/v1/file/delete/`;
  /**
   * 修改文件
   */
  modifyFilesUrl: string = `/api/v1/file/modify/`;
  /**
   * 上传文件
   */
  uploadFilesUrl: string = `/api/v1/file/upload/`;
  /**
   * 读取markdown文件
   */
  mdFileReadUrl: string = `/api/v1/text/md/`;
  /**
   * 读取图片文件
   */
  pictureFileReadUrl: string = `/api/v1/img/`;

}
