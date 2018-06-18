import {NgModule} from '@angular/core';
import {UploadComponent} from './upload/upload.component';
import {SharedModule} from "../shared/shared.module";
import {NgxUploaderModule} from "ngx-uploader";
import { UploadIndexComponent } from './upload-index/upload-index.component';
import { UploadFilesComponent } from './upload-files/upload-files.component';

@NgModule({
  imports: [
    SharedModule,
    NgxUploaderModule
  ],
  declarations: [UploadComponent, UploadIndexComponent, UploadFilesComponent],
  exports: [UploadIndexComponent]
})
export class UploadModule { }
