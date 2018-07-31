import {Component, OnInit} from '@angular/core';
import {FileItem} from "../../domain/file-item";
import {FileService} from "../../service/file.service";
import {ActivatedRoute} from "@angular/router";
import {DomSanitizer, SafeUrl} from "@angular/platform-browser";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-office',
  templateUrl: './office.component.html',
  styleUrls: ['./office.component.scss']
})
export class OfficeComponent implements OnInit {

  fileItem: FileItem;

  officeUrl: SafeUrl;

  constructor(private fileService: FileService,
              private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    let fileName = this.route.snapshot.paramMap.get('name');
    let passwd = this.route.snapshot.paramMap.get('passwd');

    if (fileName && passwd) {
      this.fileService.readOfficeFile(fileName, passwd)
        .subscribe(item => {
          let temp;
          if (environment.production) {
            temp = encodeURIComponent(`${window.location.protocol}//${window.location.host}/api/v1/office/${item.fileContent}`);
          } else {
            temp = encodeURIComponent(`http://quding.free.ngrok.cc/api/v1/office/${item.fileContent}`);
          }
          this.officeUrl = this.sanitizer
            .bypassSecurityTrustResourceUrl(`https://view.officeapps.live.com/op/view.aspx?src=${temp}`);

          this.fileItem = item;
        })
    }
  }

}
