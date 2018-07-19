import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {User} from "../../domain/user";
import {UserService} from "../../service/user.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  user: User;

  @Output('openSidebar')
  private sidebar = new EventEmitter<any>();



  constructor(private userService: UserService,
              private route: Router) {
  }

  ngOnInit(): void {
    this.userService.subscriseUser()
      .subscribe(x => {
        this.user = x;
      });
  }

  toLogin() {
    if (this.user) {
      this.route.navigateByUrl("upload");
    } else {
      this.route.navigateByUrl("login");
    }
  }

  /**
   * 触发侧边栏
   */
  triggerSideBar() {
    this.sidebar.emit();
  }
}
