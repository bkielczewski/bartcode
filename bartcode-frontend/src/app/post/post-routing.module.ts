import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { PagingUrlResolver } from './shared/paging-url.resolver';
import { PostComponent } from './post/post.component';
import { PostResolver } from './shared/post.resolver';
import { PostService } from './shared/post.service';
import { PostsComponent } from './posts/posts.component';
import { PostsService } from './shared/posts.service';
import { RecentPostsResolver } from './shared/recent-posts.resolver';
import { TagPostsResolver } from './shared/tag-posts.resolver';
import { YearMonthPostsResolver } from './shared/year-month-posts.resolver';
import { YearPostsResolver } from './shared/year-posts.resolver';
import { year, yearMonth } from './post-routing-url-matchers';

const routes: Routes = [
  {
    path: 'blog',
    component: PostsComponent,
    pathMatch: 'full',
    resolve: {
      posts: RecentPostsResolver,
      pagingUrl: PagingUrlResolver
    }
  },
  {
    path: 'blog/page/:page',
    component: PostsComponent,
    pathMatch: 'full',
    resolve: {
      posts: RecentPostsResolver,
      pagingUrl: PagingUrlResolver
    }
  },
  {
    matcher: year,
    component: PostsComponent,
    pathMatch: 'full',
    resolve: {
      posts: YearPostsResolver,
      pagingUrl: PagingUrlResolver
    }
  },
  {
    matcher: yearMonth,
    component: PostsComponent,
    pathMatch: 'full',
    resolve: {
      posts: YearMonthPostsResolver,
      pagingUrl: PagingUrlResolver
    }
  },
  {
    path: ':year/:month/:slug',
    component: PostComponent,
    pathMatch: 'full',
    resolve: {
      post: PostResolver,
      pagingUrl: PagingUrlResolver
    }
  },
  {
    path: ':year/:month/:slug/page/:page',
    component: PostComponent,
    pathMatch: 'full',
    resolve: {
      post: PostResolver,
      pagingUrl: PagingUrlResolver
    }
  },
  {
    path: 'tag/:tag',
    component: PostsComponent,
    pathMatch: 'full',
    resolve: {
      posts: TagPostsResolver,
      pagingUrl: PagingUrlResolver
    }
  },
  {
    path: 'tag/:tag/page/:page',
    component: PostsComponent,
    pathMatch: 'full',
    resolve: {
      posts: TagPostsResolver,
      pagingUrl: PagingUrlResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
  providers: [
    PostResolver,
    PostService,
    PostsService,
    RecentPostsResolver,
    TagPostsResolver,
    YearMonthPostsResolver,
    YearPostsResolver,
    PagingUrlResolver
  ]
})
export class PostRoutingModule {
}
