import { Injectable } from '@angular/core';
import {Observable} from "rxjs/internal/Observable";
import {FileItem} from "../domain/file-item";
import {HttpClient} from "@angular/common/http";
import {Config} from "../domain/config";
import {map} from "rxjs/operators";
import {ApiWrapper} from "../domain/api-wrapper";

@Injectable({
  providedIn: 'root'
})
export class FileService {

  constructor(private httpClient: HttpClient,
              private config: Config) { }

  /**
   * 得到上传列表
   */
  getAllFiles(): Observable<FileItem[]> {
    return this.httpClient.get(this.config.allFilesUrl)
      .pipe(map(resp => resp['data'] as FileItem[]))
  }

  /**
   * 删除一个文件
   * @param {string} fileMd5 要删除的文件标识
   */
  deleteFile(fileMd5:string): Observable<ApiWrapper> {
    return this.httpClient
      .post(this.config.deleteFilesUrl,null,{params:{'fileMd5':fileMd5}})
      .pipe(map(resp => resp as ApiWrapper))
  }


}
