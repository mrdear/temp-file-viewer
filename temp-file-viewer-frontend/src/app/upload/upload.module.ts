import {NgModule} from '@angular/core';
import {UploadComponent} from './upload/upload.component';
import {SharedModule} from "../shared/shared.module";
import {NgxUploaderModule} from "ngx-uploader";
import {UploadIndexComponent} from './upload-index/upload-index.component';
import {UploadFilesComponent} from './upload-files/upload-files.component';
import {UploadRoutingModule} from "./upload.route";
import {FileDialogComponent} from './file-dialog/file-dialog.component';

@NgModule({
  imports: [
    SharedModule,
    NgxUploaderModule,
    UploadRoutingModule
  ],
  declarations: [UploadComponent, UploadIndexComponent, UploadFilesComponent, FileDialogComponent],
  exports: [UploadIndexComponent],
  entryComponents: [FileDialogComponent]
})
export class UploadModule {
}
