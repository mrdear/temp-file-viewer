import {NgModule} from '@angular/core';
import {UploadComponent} from './upload/upload.component';
import {SharedModule} from "../shared/shared.module";
import {NgxUploaderModule} from "ngx-uploader";

@NgModule({
  imports: [
    SharedModule,
    NgxUploaderModule
  ],
  declarations: [UploadComponent],
  exports: [UploadComponent]
})
export class UploadModule { }
