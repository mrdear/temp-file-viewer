import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {FileService} from "../../service/file.service";
import {FileItem} from "../../domain/file-item";
import {ActivatedRoute} from "@angular/router";
import {ToastService} from "../../service/toast.service";

@Component({
  selector: 'app-markdown',
  templateUrl: './markdown.component.html',
  styleUrls: ['./markdown.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class MarkdownComponent implements OnInit {

  fileItem: FileItem;

  constructor(private fileService: FileService,
              private route: ActivatedRoute,
              private toast: ToastService) {
  }

  ngOnInit(): void {
    let fileName = this.route.snapshot.paramMap.get('name');
    let passwd = this.route.snapshot.paramMap.get('passwd');
    if (fileName && passwd) {
      this.fileService.readMdFile(fileName, passwd)
        .subscribe(x => {
          if (x.fileContent.startsWith('---')) {
            x.fileContent = x.fileContent.substr(x.fileContent.substr(3).indexOf('---'));
          }
          this.fileItem = x;
        });
    } else {
      this.toast.toast("文件不存在");
    }
  }


}
