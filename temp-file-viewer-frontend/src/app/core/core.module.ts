import {NgModule, Optional, SkipSelf} from '@angular/core';
import {SharedModule} from "../shared/shared.module";
import {HeaderComponent} from './header/header.component';
import {FooterComponent} from './footer/footer.component';
import {SidebarComponent} from './sidebar/sidebar.component';

@NgModule({
  imports: [
    SharedModule
  ],
  declarations: [HeaderComponent, FooterComponent, SidebarComponent],
  exports: [HeaderComponent, FooterComponent, SidebarComponent],
})
/**
 * 网站整体布局核心模块
 */
export class CoreModule {

  /**
   * 这两个注解解决的是循环引用一次第一次加载的问题,这里实现过之后不允许CoreModule再次实例化
   * @param {CoreModule} parent
   */
  constructor(@Optional() @SkipSelf() parent: CoreModule) {
    if (parent) {
      throw new Error('模块已经存在,不能再次加载');
    }
  }

}
