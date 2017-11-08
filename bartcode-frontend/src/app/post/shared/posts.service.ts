import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Resources } from '../../shared/spring-data-rest';
import { Post } from './post';
import { HttpClient } from '@angular/common/http';
import { Pageable, PageableUtils } from '../../shared/pageable';
import { environment } from '../../../environments/environment';

@Injectable()
export class PostsService {

  private static DEFAULT_PAGE_SIZE: string = '5';

  constructor(private http: HttpClient) {
  }

  getRecentPosts(pageable?: Pageable): Observable<Resources<Post>> {
    return this.http.get<Resources<Post>>(environment.serviceUrl + '/posts/search/recent',
      { params: PageableUtils.getHttpParams(pageable) });
  }

  getPostsByYear(year: number, pageable?: Pageable): Observable<Resources<Post>> {
    const params = PageableUtils.getHttpParams(pageable)
      .set('year', year.toString());
    return this.http.get<Resources<Post>>(environment.serviceUrl + '/posts/search/year',
      { params: params });
  }

  getPostsByYearMonth(year: number, month: number, pageable?: Pageable): Observable<Resources<Post>> {
    const params = PageableUtils.getHttpParams(pageable)
      .set('year', year.toString())
      .set('month', month.toString());
    return this.http.get<Resources<Post>>(environment.serviceUrl + '/posts/search/yearMonth',
      { params: params });
  }

  getPostsByTag(tag: string, pageable?: Pageable): Observable<Resources<Post>> {
    const params = PageableUtils.getHttpParams(pageable)
      .set('tag', tag);
    return this.http.get<Resources<Post>>(environment.serviceUrl + '/posts/search/tag',
      { params: params });
  }

}
