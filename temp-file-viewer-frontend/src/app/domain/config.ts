/**
 * 全站配置
 */
export class Config {

  base_url: string = 'http://127.0.0.1:4200';

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

}
