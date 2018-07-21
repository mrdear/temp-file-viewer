import {Component, OnInit} from '@angular/core';
import {FileService} from "../../service/file.service";
import {ActivatedRoute} from "@angular/router";
import {FileItem} from "../../domain/file-item";

@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrls: ['./picture.component.scss']
})
export class PictureComponent implements OnInit {

  fileItem: FileItem;

  constructor(private fileService: FileService,
              private route: ActivatedRoute) { }

  ngOnInit() {
    let fileName = this.route.snapshot.paramMap.get('name');
    let passwd = this.route.snapshot.paramMap.get('passwd');

    if (fileName && passwd) {
      this.fileService.readPictureFile(fileName, passwd)
        .subscribe(item => {
          this.fileItem = item;
        })
    }

  }



}
