import { Injectable } from '@angular/core';
import { Response } from '@angular/http';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Document } from './document';
import { Observable } from 'rxjs';
import { DocumentService } from './document.service';

@Injectable()
export class DocumentResolver implements Resolve<Document> {

  constructor(private documentService: DocumentService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
    Observable<Document>
    | Promise<Document>
    | Document {
    const relativeUrl = '/' + route.url[0];
    return this.documentService.getDocumentByRelativeUrl(relativeUrl)
      .catch((err: Response) => {
        this.router.navigate(['/error'], { queryParams: { code: err.status } });
        return Promise.resolve(null);
      });
  }

}
