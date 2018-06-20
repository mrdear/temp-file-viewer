import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserService} from "./user.service";

export {
  UserService
}

@NgModule()
export class ServiceModule {
  /**
   * 利用这种方式可以针对不同模块定制不同的服务
   */
  static forRoot() {
    return {
      ngModule: ServiceModule,
      providers: [
        UserService
      ]
    };
  }
}
