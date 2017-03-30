import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PostsComponent } from './posts/posts.component';
import { RecentPostsResolver } from './posts/recent-posts.resolver';
import { YearPostsResolver } from './posts/year-posts.resolver';
import { YearMonthPostsResolver } from './posts/year-month-posts.resolver';
import { PostsService } from './posts/posts.service';
import { year, yearMonth } from './post-routing-url-matchers';

const routes: Routes = [
  { path: 'blog', component: PostsComponent, pathMatch: 'full', resolve: { posts: RecentPostsResolver } },
  { matcher: year, component: PostsComponent, pathMatch: 'full', resolve: { posts: YearPostsResolver } },
  { matcher: yearMonth, component: PostsComponent, pathMatch: 'full', resolve: { posts: YearMonthPostsResolver } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    RecentPostsResolver,
    YearPostsResolver,
    YearMonthPostsResolver,
    PostsService
  ]
})
export class PostRoutingModule {
}
