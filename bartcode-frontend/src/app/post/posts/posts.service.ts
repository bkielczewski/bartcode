import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';

import { Observable } from 'rxjs';
import { Resources } from '../../spring-data-rest';
import { Post } from '../post';

import { environment } from '../../../environments/environment';


@Injectable()
export class PostsService {

  private static DEFAULT_PAGE_SIZE: string = '5';

  constructor(private http: Http) {
  }

  getRecentPosts(page?: number, size?: number): Observable<Post> {
    return this.http.get(environment.serviceUrl + '/posts/search/recent',
      { search: this.getParams(page, size) })
      .flatMap((response: Response) => this.extractResponseData(response))
      .publishReplay()
      .refCount();
  }

  private getParams(page?: number, size?: number, year?: number, month?: number): URLSearchParams {
    const params: URLSearchParams = new URLSearchParams();
    if (year) {
      params.set('year', year.toString());
    }
    if (month) {
      params.set('month', month.toString());
    }
    params.set('page', page ? page.toString() : '0');
    params.set('size', size ? size.toString() : PostsService.DEFAULT_PAGE_SIZE);
    return params;
  }

  private extractResponseData(response: Response): Post[] {
    return (<Resources<Post>> response.json())._embedded['posts'];
  }

  getPostsByYear(year: number, page?: number, size?: number): Observable<Post> {
    return this.http.get(environment.serviceUrl + '/posts/search/year',
      { search: this.getParams(page, size, year) })
      .flatMap((response: Response) => this.extractResponseData(response))
      .publishReplay()
      .refCount();
  }

  getPostsByYearMonth(year: number, month: number, page?: number, size?: number): Observable<Post> {
    return this.http.get(environment.serviceUrl + '/posts/search/yearMonth',
      { search: this.getParams(page, size, year, month) })
      .flatMap((response: Response) => this.extractResponseData(response))
      .publishReplay()
      .refCount();
  }

}
