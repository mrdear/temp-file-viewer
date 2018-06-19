import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }

  userFormControl = new FormControl('', [
    Validators.required
  ]);

  passFormControl = new FormControl('', [
    Validators.required
  ]);

}
