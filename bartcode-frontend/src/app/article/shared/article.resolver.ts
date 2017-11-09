import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { NotFoundError } from '../../shared/not-found-error';
import { HttpErrorResponse } from '@angular/common/http';
import { Article } from './article';
import { ArticleService } from './article.service';

@Injectable()
export class ArticleResolver implements Resolve<Article> {

  constructor(private articleService: ArticleService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Article> {
    const relativeUrl = '/' + route.url[0];
    return this.articleService.getArticleByRelativeUrl(relativeUrl)
      .catch((err: HttpErrorResponse) => {
        this.router.navigate(['/error'], { queryParams: { code: err.status } });
        return Observable.throw(err.status == 404 ?
          new NotFoundError('Article Not Found, url=' + relativeUrl) :
          new Error('Couldn\'t get article, cause: ' + err));
      });
  }

}
