import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { Post } from './post';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable()
export class PostService {

  constructor(private http: HttpClient) {
  }

  getPostByRelativeUrl(relativeUrl: string): Observable<Post> {
    const params = new HttpParams().set('relativeUrl', relativeUrl);
    return this.http.get<Post>(environment.serviceUrl + '/posts/search/relativeUrl', { params: params });
  }

}
