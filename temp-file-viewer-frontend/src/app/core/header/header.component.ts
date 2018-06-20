import {Component, Input, OnInit} from '@angular/core';
import {User} from "../../domain/user";
import {UserService} from "../../service/user.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user: User;

  constructor(private userService: UserService) {
  }

  ngOnInit(): void {
    this.userService.subscriseUser()
      .subscribe(x => {
        this.user = x;
      });
  }
}
