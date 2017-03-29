import { NgModule } from '@angular/core';
import { RouterModule, Routes, UrlSegment } from '@angular/router';
import { PostsComponent } from './posts/posts.component';
import { RecentPostsResolver } from './posts/recent-posts.resolver';
import { YearPostsResolver } from './posts/year-posts.resolver';
import { YearMonthPostsResolver } from './posts/year-month-posts.resolver';
import { PostsService } from './posts/posts.service';
import { UrlMatchResult } from '@angular/router/src/config';

const routes: Routes = [
  { path: 'blog', component: PostsComponent, pathMatch: 'full', resolve: { posts: RecentPostsResolver } },
  { matcher: year, component: PostsComponent, pathMatch: 'full', resolve: { posts: YearPostsResolver } },
  { matcher: yearMonth, component: PostsComponent, pathMatch: 'full', resolve: { posts: YearMonthPostsResolver } }
];

function year(url: UrlSegment[]): UrlMatchResult {
  return (url.length == 1 && /^([0-9]{4,})$/.test(url[0].path)) ? { consumed: url, posParams: { year: url[0] } } : null;
}

function yearMonth(url: UrlSegment[]): UrlMatchResult {
  return (url.length == 2 && /^([0-9]{4,})$/.test(url[0].path) && /^([0-9]{2,})$/.test(url[1].path)) ?
    { consumed: url, posParams: { year: url[0], month: url[1] } } : null;
}

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
