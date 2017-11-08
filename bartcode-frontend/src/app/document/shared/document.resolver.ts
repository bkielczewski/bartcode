import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Document } from './document';
import { Observable } from 'rxjs/Observable';
import { DocumentService } from './document.service';
import { NotFoundError } from '../../shared/not-found-error';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable()
export class DocumentResolver implements Resolve<Document> {

  constructor(private documentService: DocumentService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Document> {
    const relativeUrl = '/' + route.url[0];
    return this.documentService.getDocumentByRelativeUrl(relativeUrl)
      .catch((err: HttpErrorResponse) => {
        this.router.navigate(['/error'], { queryParams: { code: err.status } });
        return Observable.throw(err.status == 404 ?
          new NotFoundError('Document Not Found, url=' + relativeUrl) :
          new Error('Couldn\'t get document, cause: ' + err));
      });
  }

}
