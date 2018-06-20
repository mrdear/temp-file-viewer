import {Injectable} from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
  HttpResponse
} from "@angular/common/http";
import {Observable} from "rxjs/internal/Observable";
import {tap} from "rxjs/operators";
import {ToastService} from "./toast.service";

@Injectable()
export class HttpInterceptorService implements HttpInterceptor {

  constructor(private toast: ToastService) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(tap(event => {
        if (event instanceof HttpResponse) {
          if (event.status != 200) {
            this.toast.toast('请求出错');
          }
        }
      }))
      ;
  }
}
