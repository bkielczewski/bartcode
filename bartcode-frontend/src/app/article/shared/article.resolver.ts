import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Resolve, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { Article } from './article';
import { ArticleService } from './article.service';

@Injectable()
export class ArticleResolver implements Resolve<Article> {

  constructor(private articleService: ArticleService) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Article> {
    const relativeUrl = '/' + route.url[0];
    return this.articleService.getArticleByRelativeUrl(relativeUrl);
  }

}
