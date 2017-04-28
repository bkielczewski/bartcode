import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { TagPostCount } from './tag-post-count';
import { Resources } from '../../spring-data-rest';

@Injectable()
export class TagPostCountService {

  constructor(private http: Http) {
  }

  getTagCounts(): Observable<TagPostCount> {
    return this.http.get(environment.serviceUrl + '/posts/search/tagPostCounts')
      .catch(() => Observable.empty())
      .flatMap((response: Response) => (<Resources<TagPostCount>> response.json())._embedded['tagPostCounts'])
      .publishReplay()
      .refCount();
  }

}
