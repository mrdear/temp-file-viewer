import {Component, OnInit} from '@angular/core';
import {SideMenu} from "../../domain/side-menu";
import {Router} from "@angular/router";

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  mainSideMenus: SideMenu[] = [
    {
      icon:"send",
      name: "文件分享",
      url: "upload"
    }
  ];

  aboutSideMenus: SideMenu[] = [
    {
      icon:"chat",
      name: "Github",
      url: "https://github.com/mrdear/temp-file-viewer"
    }
  ];

  constructor(private route: Router) {
  }

  ngOnInit() {

  }

  /**
   * 定位到具体页面
   */
  redirectPath(menu: SideMenu) {
    if (menu.url.startsWith("http")) {
      window.open(menu.url);
    } else {
      this.route.navigateByUrl(menu.url);
    }
  }
}
