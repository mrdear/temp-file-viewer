import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {UploaderOptions, UploadFile, UploadInput, UploadOutput, UploadStatus} from "ngx-uploader";
import {Config} from "../../domain/config";
import {FileItem} from "../../domain/file-item";
import {ApiWrapper} from "../../domain/api-wrapper";
import {ToastService} from "../../service/toast.service";

@Component({
  selector: 'app-upload',
  templateUrl: './upload.component.html',
  styleUrls: ['./upload.component.scss']
})
export class UploadComponent implements OnInit {
  /**
   * 上传成功事件
   */
  @Output()
  uploadSuccess: EventEmitter<FileItem> = new EventEmitter<FileItem>();

  files: UploadFile[];

  uploadInput: EventEmitter<UploadInput>;

  dragOver: boolean;

  options: UploaderOptions;

  constructor(private config: Config,
              private toast: ToastService) {
    this.options = { concurrency: 1 };
    this.files = [];
    this.uploadInput = new EventEmitter<UploadInput>();
  }

  onUploadOutput(output: UploadOutput): void {
    if (output.type === 'allAddedToQueue') {
      const event: UploadInput = {
        type: 'uploadAll',
        headers: { "Access-Control-Allow-Origin": document.location.host },
        url: this.config.uploadFilesUrl,
        method: 'POST',
      };
      setTimeout(() => this.uploadInput.emit(event), 1000);
    } else if (output.type === 'addedToQueue' && typeof output.file !== 'undefined') {
      this.files.push(output.file);
    } else if (output.type === 'uploading' && typeof output.file !== 'undefined') {
      const index = this.files.findIndex(file => typeof output.file !== 'undefined' && file.id === output.file.id);
      this.files[index] = output.file;
    } else if (output.type === 'removed') {
      this.files = this.files.filter((file: UploadFile) => file !== output.file);
    } else if (output.type === 'dragOver') {
      this.dragOver = true;
    } else if (output.type === 'dragOut') {
      this.dragOver = false;
    } else if (output.type === 'drop') {
      this.dragOver = false;
    } else if (output.type === 'rejected' && typeof output.file !== 'undefined') {
      console.log(output.file.name + ' rejected');
    } else if (output.type === 'done') {
      let resp = output.file.response as ApiWrapper;
      if (resp.status == 2000) {
        this.uploadSuccess.emit(resp.data as FileItem);
      } else {
        this.toast.toast(resp.message);
      }
    }

    this.files = this.files.filter(file => file.progress.status !== UploadStatus.Done);
  }

  cancelUpload(id: string): void {
    this.uploadInput.emit({ type: 'cancel', id: id });
  }

  removeFile(id: string): void {
    this.uploadInput.emit({ type: 'remove', id: id });
  }

  removeAllFiles(): void {
    this.uploadInput.emit({ type: 'removeAll' });
  }

  ngOnInit(): void {
  }

}
