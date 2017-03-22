import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Router } from '@angular/router';
import { Article } from './article';
import { Observable } from 'rxjs';
import { ArticleService } from './article.service';

@Injectable()
export class ArticleResolver implements Resolve<Article> {

  constructor(private articleService: ArticleService, private router: Router) {
  }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<Article>|Promise<Article>|Article {
    const id = route.params['id'];
    return this.articleService.getArticleById(id)
      .catch(() => {
        this.router.navigate(['/404']);
        return Promise.resolve(null);
      });
  }

}
