import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { DatePostCount } from './date-post-count';
import { HalUtils, Resources } from '../../shared/spring-data-rest';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';

@Injectable()
export class DatePostCountService {

  constructor(private http: HttpClient) {
  }

  getDateCounts(): Observable<DatePostCount> {
    return this.http.get<Resources<DatePostCount>>(environment.serviceUrl + '/posts/search/datePostCounts')
      .flatMap(resources => HalUtils.getEmbedded('datePostCounts', resources));
  }

}
