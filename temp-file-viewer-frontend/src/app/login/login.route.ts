import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {LoginComponent} from './login/login.component';


const routes: Routes = [
  {path: 'login', component: LoginComponent},
];


@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
/**
 * 子路由,推崇给每一个页面模块建立自己的路由
 * 整体路由是单例的RouterModule中提供的实例,因此只需要在对应模块中forChild就可以添加子路由进去
 */
export class LoginRoutingModule {
}
