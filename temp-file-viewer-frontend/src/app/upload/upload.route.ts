import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UploadIndexComponent} from "./upload-index/upload-index.component";


const routes: Routes = [
  {path: 'upload', component: UploadIndexComponent},
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
/**
 * 子路由,推崇给每一个页面模块建立自己的路由
 * 整体路由是单例的RouterModule中提供的实例,因此只需要在对应模块中forChild就可以添加子路由进去
 */
export class UploadRoutingModule {
}
