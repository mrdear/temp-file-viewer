import {NgModule} from '@angular/core';
import {UserService} from "./user.service";
import {FileService} from "./file.service";
import {ToastService} from "./toast.service";

export {
  UserService,
  FileService,
  ToastService
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
        UserService,
        FileService,
        ToastService
      ]
    };
  }
}
