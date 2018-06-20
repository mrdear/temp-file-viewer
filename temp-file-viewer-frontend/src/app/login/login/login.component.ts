import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {UserService} from "../../service/user.service";
import {MatSnackBar} from "@angular/material";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  username: string;

  passwd: string;

  constructor(private userService: UserService,
              private snackBar: MatSnackBar,
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
      this.snackBar.open('信息未填写完整', 'Hide', {
        duration: 2000
      });
      return;
    }
    this.userService.login(this.username,this.passwd)
      .subscribe(x => {
        // 登录成功
        if (x.status == 2000) {
          this.route.navigateByUrl("")
        } else {
          this.snackBar.open(x.message, 'Hide', {duration: 2000});
        }
      })
  }

  reset() {
    this.userFormControl.reset();
    this.passFormControl.reset();
  }

}
