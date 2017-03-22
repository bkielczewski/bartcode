import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';
import { Article } from './article';
import { environment } from '../../environments/environment';

@Injectable()
export class ArticleService {

  constructor(private http: Http) {
  }

  getArticleById(id: string): Observable<Article> {
    return this.http.get(environment.serviceUrl + '/articles/' + id)
      .map((response: Response) => (<Article> response.json()));
  }
}
