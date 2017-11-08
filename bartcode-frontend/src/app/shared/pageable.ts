import { MatPaginator, MatSort } from '@angular/material';
import { HttpParams } from '@angular/common/http';

export class Pageable {
  page = 0;
  size = 20;
  sort?: string;
  direction?: Direction;
}

export enum Direction {
  ASC, DESC
}

export class PageableUtils {

  static fromPage(page?: number, size?: number) {
    const pageable = new Pageable();
    if (page) {
      pageable.page = page;
    }
    if (size) {
      pageable.size = size;
    }
    return pageable;
  }

  static fromPaginatorAndSort(paginator: MatPaginator, sort: MatSort) {
    const pageable = new Pageable();
    pageable.page = paginator.pageIndex;
    pageable.size = paginator.pageSize;
    pageable.sort = sort.active;
    pageable.direction = sort.direction === 'asc' ? Direction.ASC : Direction.DESC;
    return pageable;
  }

  static getHttpParams(pageable: Pageable): HttpParams {
    let params: HttpParams = new HttpParams();
    if (pageable) {
      params = params
        .set('page', pageable.page.toString())
        .set('size', pageable.size.toString());
      if (pageable.sort) {
        const direction = (pageable.direction === Direction.ASC) ? 'asc' : 'desc';
        const sort = pageable.sort + ',' + direction;
        params = params.set('sort', sort);
      }
    }
    return params;
  }

}
