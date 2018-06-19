import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {Config} from "./domain/config";
import {environment} from "../environments/environment";
import {CoreModule} from "./core/core.module";
import {SharedModule} from "./shared/shared.module";
import {UploadModule} from "./upload/upload.module";
import {LoginModule} from "./login/login.module";
import {AppRoutingModule} from "./app.route";

@NgModule({
  declarations: [
    AppComponent,
  ],
  imports: [
    BrowserModule,
    SharedModule,
    CoreModule,
    UploadModule,
    LoginModule,
    AppRoutingModule,
  ],
  // 利用CI保证全局唯一
  providers: [
    {
      provide: Config,
      useFactory: () => {return AppModule.baseConfig()},
      deps: []
    }
  ],
  bootstrap: [AppComponent]
})
/**
 * angular cli help
 * https://github.com/angular/angular-cli/wiki/stories
 */
export class AppModule {

  /**
   * 返回网站配置
   */
  static baseConfig(): Config {
    if (environment.production) {
      return new Config('//api.ifreehub.cn')
    } else {
      return new Config();
    }
  }

}
