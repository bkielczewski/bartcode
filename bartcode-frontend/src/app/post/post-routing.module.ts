import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PostComponent } from './post/post.component';
import { PostResolver } from './post/post.resolver';
import { PostService } from './post/post.service';
import { PostsComponent } from './posts/posts.component';
import { PostsService } from './posts/posts.service';
import { RecentPostsResolver } from './posts/recent-posts.resolver';
import { YearMonthPostsResolver } from './posts/year-month-posts.resolver';
import { YearPostsResolver } from './posts/year-posts.resolver';
import { year, yearMonth } from './post-routing-url-matchers';

const routes: Routes = [
  { path: 'blog', component: PostsComponent, pathMatch: 'full', resolve: { posts: RecentPostsResolver } },
  { matcher: year, component: PostsComponent, pathMatch: 'full', resolve: { posts: YearPostsResolver } },
  { matcher: yearMonth, component: PostsComponent, pathMatch: 'full', resolve: { posts: YearMonthPostsResolver } },
  { path: ':year/:month/:slug', component: PostComponent, pathMatch: 'full', resolve: { post: PostResolver } }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    PostResolver,
    PostService,
    PostsService,
    RecentPostsResolver,
    YearMonthPostsResolver,
    YearPostsResolver
  ]
})
export class PostRoutingModule {
}
