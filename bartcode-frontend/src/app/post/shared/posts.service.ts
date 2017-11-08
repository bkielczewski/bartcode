import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { Resources } from '../../shared/spring-data-rest';
import { Post } from './post';

import { environment } from '../../../environments/environment';


@Injectable()
export class PostsService {

  private static DEFAULT_PAGE_SIZE: string = '5';

  constructor(private http: Http) {
  }

  getRecentPosts(page?: number, size?: number): Observable<Resources<Post>> {
    return this.http.get(environment.serviceUrl + '/posts/search/recent',
      { search: this.getParams(page, size) })
      .map((response: Response) => response.json());
  }

  private getParams(page?: number, size?: number, year?: number, month?: number, tag?: string): URLSearchParams {
    const params: URLSearchParams = new URLSearchParams();
    if (year) {
      params.set('year', year.toString());
    }
    if (month) {
      params.set('month', month.toString());
    }
    if (tag) {
      params.set('tag', tag);
    }
    params.set('page', page ? page.toString() : '0');
    params.set('size', size ? size.toString() : PostsService.DEFAULT_PAGE_SIZE);
    return params;
  }

  getPostsByYear(year: number, page?: number, size?: number): Observable<Resources<Post>> {
    return this.http.get(environment.serviceUrl + '/posts/search/year',
      { search: this.getParams(page, size, year) })
      .map((response: Response) => response.json());
  }

  getPostsByYearMonth(year: number, month: number, page?: number, size?: number): Observable<Resources<Post>> {
    return this.http.get(environment.serviceUrl + '/posts/search/yearMonth',
      { search: this.getParams(page, size, year, month) })
      .map((response: Response) => response.json());
  }

  getPostsByTag(tag: string, page?: number, size?: number): Observable<Resources<Post>> {
    return this.http.get(environment.serviceUrl + '/posts/search/tag',
      { search: this.getParams(page, size, null, null, tag) })
      .map((response: Response) => response.json());
  }

}
