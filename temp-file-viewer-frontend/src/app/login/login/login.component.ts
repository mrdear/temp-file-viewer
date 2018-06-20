import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {MatSnackBar} from "@angular/material";
import {Router} from "@angular/router";
import {ToastService} from "../../service/toast.service";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string;

  passwd: string;

  constructor(private userService: UserService,
              private toast: ToastService,
              private route: Router) { }

  ngOnInit() {
  }

  userFormControl = new FormControl('', [
    Validators.required
  ]);

  passFormControl = new FormControl('', [
    Validators.required
  ]);


  login() {
    if (this.userFormControl.invalid || this.passFormControl.invalid) {
      this.toast.toast('信息未填写完整');
      return;
    }
    this.userService.login(this.username,this.passwd)
      .subscribe(x => {
        // 登录成功
        if (x.status == 2000) {
          this.route.navigateByUrl("upload");
        } else {
          this.toast.toast(x.message);
        }
      })
  }

  reset() {
    this.userFormControl.reset();
    this.passFormControl.reset();
  }

}
