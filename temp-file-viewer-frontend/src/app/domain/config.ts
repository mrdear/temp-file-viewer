/**
 * 全站配置
 */
export class Config {

  base_url: string = 'http://127.0.0.1:8080';

  constructor(base_url?: string) {
    this.base_url = base_url;
  }


}
