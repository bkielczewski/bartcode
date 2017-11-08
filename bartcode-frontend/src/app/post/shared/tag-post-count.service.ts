import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { TagPostCount } from './tag-post-count';
import { HalUtils, Resources } from '../../shared/spring-data-rest';
import { HttpClient } from '@angular/common/http';

@Injectable()
export class TagPostCountService {

  constructor(private http: HttpClient) {
  }

  getTagCounts(): Observable<TagPostCount> {
    return this.http.get<Resources<TagPostCount>>(environment.serviceUrl + '/posts/search/tagPostCounts')
      .flatMap(resources => HalUtils.getEmbedded('tagPostCounts', resources));
  }

}
