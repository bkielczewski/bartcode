import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class PagingUrlResolver implements Resolve<string> {

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): string {
    const path = decodeURI(route.url.join('/'));
    if (route.params['page']) {
      return path.substring(0, path.indexOf('/page'));
    } else {
      return path;
    }
  }

}
