import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArticleService } from './shared/article.service';
import { ArticleComponent } from './article.component';
import { ArticleResolver } from './shared/article.resolver';

const routes: Routes = [
  {
    path: 'privacy-and-cookies',
    component: ArticleComponent,
    pathMatch: 'full',
    resolve: {
      article: ArticleResolver
    }
  },
  {
    path: 'about',
    component: ArticleComponent,
    pathMatch: 'full',
    resolve: {
      article: ArticleResolver
    }
  }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ],
  providers: [
    ArticleService,
    ArticleResolver
  ]
})
export class ArticleRoutingModule {
}
