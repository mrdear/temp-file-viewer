import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {FileItem} from "../../domain/file-item";
import {ToastService} from "../../service/toast.service";

/**
 * 数据
 */
export interface FileDialogData {
  action: string
  file: FileItem
}


@Component({
  selector: 'app-file-dialog',
  templateUrl: './file-dialog.component.html',
  styleUrls: ['./file-dialog.component.scss']
})
export class FileDialogComponent implements OnInit {

  action: string;

  file: FileItem;

  oldFile: FileItem = new FileItem();

  constructor(
    public dialogRef: MatDialogRef<FileDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: FileDialogData,
    private toast: ToastService) {
    this.action = data.action;
    this.file = data.file;
    Object.assign(this.oldFile,data.file)
  }

  ngOnInit() {
  }

  update({valid}, $event) {
    if (valid) {
      if (this.isChange()) {
        this.dialogRef.close(null);
      } else {
        this.dialogRef.close(this.file);
      }
    } else {
      this.toast.toast("请补充信息");
    }
  }

  /**
   * 文件是否改变
   */
  private isChange(): boolean {
    return this.oldFile.passwd === this.file.passwd
      && this.file.fileName == this.oldFile.fileName;
  }

}
