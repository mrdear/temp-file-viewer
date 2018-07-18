import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FileItem} from "../../domain/file-item";
import {MatDialog} from "@angular/material";
import {FileDialogComponent} from "../file-dialog/file-dialog.component";
import {Router} from "@angular/router";

@Component({
  selector: 'app-upload-files',
  templateUrl: './upload-files.component.html',
  styleUrls: ['./upload-files.component.scss']
})
export class UploadFilesComponent implements OnInit {

  @Input()
  fileItems: FileItem[] = [];

  @Output()
  deleteFile: EventEmitter<string> = new EventEmitter<string>();
  @Output()
  updateFile: EventEmitter<FileItem> = new EventEmitter<FileItem>();

  constructor(private dialog: MatDialog,
              private route: Router) {
  }

  ngOnInit() {
  }

  /**
   * 发出删除文件事件
   */
  delete(fileMd5: string) {
    this.deleteFile.emit(fileMd5);
  }

  /**
   * 打开文件展示页面
   */
  openFile(item: FileItem) {
    let filePath = FileItem.getFilePath(item);
    this.route.navigateByUrl(filePath);
  }

  /**
   * 打开文件详情框
   */
  update(item: FileItem) {
    let dialogRef = this.dialog.open(FileDialogComponent,{
      panelClass:'dashboard-background',
      position: {top:"15%"},
      data: {
        action:'update',
        file: Object.assign(new FileItem(),item),
      }
    });
    dialogRef.afterClosed().subscribe(result => {
      if (result && result != item) {
        this.updateFile.emit(result);
      }
    });
  }
}
