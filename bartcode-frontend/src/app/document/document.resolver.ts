import { Injectable } from '@angular/core';
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
    const relativeUrl = '/' + route.params['relativeUrl'];
    return this.documentService.getDocumentByRelativeUrl(relativeUrl)
      .catch(() => {
        this.router.navigate(['/404']);
        return Promise.resolve(null);
      });
  }

}
