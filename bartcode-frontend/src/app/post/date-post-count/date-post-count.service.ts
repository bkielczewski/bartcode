import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs';

import { DatePostCount } from './date-post-count';
import { Resources } from '../../spring-data-rest';

import { environment } from '../../../environments/environment';

@Injectable()
export class DatePostCountService {

  constructor(private http: Http) {
  }

  getDateCounts(): Observable<DatePostCount> {
    return this.http.get(environment.serviceUrl + '/posts/search/datePostCounts')
      .flatMap((response: Response) => (<Resources<DatePostCount>> response.json())._embedded['datePostCounts'])
      .publishReplay()
      .refCount();
  }

}
