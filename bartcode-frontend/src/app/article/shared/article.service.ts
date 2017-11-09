import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { Article } from './article';
import { environment } from '../../../environments/environment';

@Injectable()
export class ArticleService {

  constructor(private http: HttpClient) {
  }

  getArticleByRelativeUrl(relativeUrl: string): Observable<Article> {
    const params = new HttpParams().set('relativeUrl', relativeUrl);
    return this.http.get<Article>(environment.serviceUrl + '/articles/search/relativeUrl', { params: params });
  }
}
