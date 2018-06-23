import {NgModule} from '@angular/core';
import {LoginComponent} from './login/login.component';
import {SharedModule} from "../shared/shared.module";
import {LoginRoutingModule} from "./login.route";

@NgModule({
  imports: [
    SharedModule,
    LoginRoutingModule
  ],
  declarations: [LoginComponent]
})
export class LoginModule { }
