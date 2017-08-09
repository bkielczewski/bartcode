import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs';

import { Document } from './document';
import { environment } from '../../../environments/environment';

@Injectable()
export class DocumentService {

  constructor(private http: Http) {
  }

  getDocumentByRelativeUrl(relativeUrl: string): Observable<Document> {
    const params: URLSearchParams = new URLSearchParams();
    params.set('relativeUrl', relativeUrl);
    return this.http.get(environment.serviceUrl + '/documents/search/relativeUrl', { search: params })
      .map((response: Response) => <Document> response.json());
  }
}
