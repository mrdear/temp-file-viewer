import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FileItem} from "../../domain/file-item";

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

  constructor() {
  }

  ngOnInit() {
  }

  delete(fileMd5: string) {
    this.deleteFile.emit(fileMd5);
  }

}
