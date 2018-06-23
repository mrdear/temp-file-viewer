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
import {DisplayModule} from "./display/display.module";

export let baseConfig = () => {
  console.log(environment.production);
  let config = new Config();
  if (environment.production) {
    config.base_url = '/';
  } else {
    config.base_url = 'http://localhost:4200';
  }
  return config;
};

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
    DisplayModule,
  ],
  // 利用CI保证全局唯一
  providers: [
    {
      provide: Config,
      useFactory: baseConfig,
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


}
