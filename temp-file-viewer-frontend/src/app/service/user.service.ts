import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {User} from "../domain/user";
import {Config} from "../domain/config";
import {map, tap} from "rxjs/operators";
import {Observable} from "rxjs/internal/Observable";
import {MatSnackBar} from "@angular/material";
import {ApiWrapper} from "../domain/api-wrapper";
import {Subject} from "rxjs/internal/Subject";
import {BehaviorSubject} from "rxjs/internal/BehaviorSubject";

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userSubject: BehaviorSubject<User> = new BehaviorSubject<User>(null);

  constructor(private httpClient: HttpClient,
              private config: Config,
              private snackBar: MatSnackBar) {
    // BehaviorSubject能保存旧值,因此这里先订阅一下
    this.profile().subscribe(x => {
      this.userSubject.next(x);
    })
  }


  /**
   * 登录
   * @param {string} username 用户名
   * @param {string} passwd 密码
   */
  login(username: string, passwd: string): Observable<ApiWrapper> {
    return this.httpClient
      .post(this.config.loginUrl, null,
        {
          params:
            {
              'username': username,
              'passwd': passwd
            }
        })
      .pipe(
        map(resp => resp as ApiWrapper),
        tap(x => {
          if (x.status == 2000) {
            this.profile()
              .subscribe(x => this.userSubject.next(x))
          }
        })
      )
  }

  /**
   * 订阅用户
   */
  subscriseUser(): Subject<User>{
    return this.userSubject;
  }

  /**
   * 登录之后获取当前用户信息
   */
  profile(): Observable<User> {
    return this.httpClient
      .get(this.config.profileUrl)
      .pipe(
        map(resp => resp['data'] as User)
      )
  }


}
