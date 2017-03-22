import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ArticleService } from './article.service';
import { ArticleComponent } from './article.component';
import { ArticleResolver } from './article.resolver';

const routes: Routes = [
  { path: ':id', component: ArticleComponent, pathMatch: 'full', resolve: { article: ArticleResolver } }
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
