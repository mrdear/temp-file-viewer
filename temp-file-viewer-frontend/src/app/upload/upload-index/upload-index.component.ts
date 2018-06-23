import {Component, OnInit} from '@angular/core';
import {FileService} from "../../service/file.service";
import {FileItem} from "../../domain/file-item";
import {ToastService} from "../../service/toast.service";

@Component({
  selector: 'app-upload-index',
  templateUrl: './upload-index.component.html',
  styleUrls: ['./upload-index.component.scss']
})
export class UploadIndexComponent implements OnInit {

  fileItems: FileItem[] = [];

  constructor(private fileService: FileService,
              private toast: ToastService) {
  }

  ngOnInit() {
    this.getAllFiles();
  }

  /**
   * 获取全部文件
   */
  getAllFiles() {
    this.fileService.getAllFiles()
      .subscribe(x => {
        if (x) {
          this.fileItems = [...x];
        }
      });
  }


  /**
   * 上传成功回调
   */
  uploadSuccess(file: FileItem) {
    this.fileItems = [file, ...this.fileItems.filter(x => x.md5Name != file.md5Name)];
  }

  /**
   * 删除文件
   * @param {string} fileMd5
   */
  delete(fileMd5: string) {
    let fileItem = this.fileItems
      .filter(x => x.md5Name === fileMd5)
      .pop();

    if (fileItem) {
      this.fileService.deleteFile(fileItem.md5Name)
        .subscribe(x => {
          if (x.status === 2000) {
            this.fileItems = [...this.fileItems.filter(x => x.md5Name != fileMd5)];
          } else {
            this.toast.toast(x.message);
          }
        })
    }
  }
}
