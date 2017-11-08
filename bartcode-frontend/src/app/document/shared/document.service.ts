import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Document } from './document';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../../environments/environment';

@Injectable()
export class DocumentService {

  constructor(private http: HttpClient) {
  }

  getDocumentByRelativeUrl(relativeUrl: string): Observable<Document> {
    const params = new HttpParams().set('relativeUrl', relativeUrl);
    return this.http.get<Document>(environment.serviceUrl + '/documents/search/relativeUrl', { params: params });
  }
}
