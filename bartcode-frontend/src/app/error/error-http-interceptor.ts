import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import 'rxjs/add/observable/fromPromise';
import 'rxjs/add/operator/mergeMap';
import { Router } from '@angular/router';

@Injectable()
export class ErrorHttpInterceptor implements HttpInterceptor {

  private static URL_ERROR = '/error';

  constructor(private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).catch((error: any) => this.handleError(error));
  }

  private handleError(error: HttpErrorResponse): Observable<any> {
    this.router.navigateByUrl(`${ErrorHttpInterceptor.URL_ERROR}/${error.status}`);
    return Observable.empty();
  }

}
