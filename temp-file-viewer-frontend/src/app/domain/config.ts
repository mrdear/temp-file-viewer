/**
 * 全站配置
 */
export class Config {

  base_url: string = 'http://localhost:4200';

  constructor(base_url?: string) {
    this.base_url = base_url;
  }

  /**
   * 登录请求
   */
  loginUrl: string = this.base_url + '/api/v1/login/';
  /**
   * 用户信息
   */
  profileUrl: string = this.base_url + '/api/v1/profile/';
  /**
   * 得到上传文件列表
   */
  allFilesUrl: string = this.base_url + '/api/v1/file/list/';
  /**
   * 删除文件
   */
  deleteFilesUrl: string = this.base_url + '/api/v1/file/delete/';
  /**
   * 上传文件
   */
  uploadFilesUrl: string = this.base_url + '/api/v1/file/upload/';
  /**
   * 读取markdown文件
   */
  mdFileReadUrl: string = this.base_url + '/api/v1/md/';

}
