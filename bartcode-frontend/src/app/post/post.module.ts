import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';

import { PostRoutingModule } from './post-routing.module';

import { DatePostCountComponent } from './date-post-count/date-post-count.component';
import { PopularPostsComponent } from './popular-posts/popular-posts.component';
import { PostsComponent } from './posts/posts.component';
import { TagPostCountComponent } from './tag-post-count/tag-post-count.component';

import { DatePostCountService } from './date-post-count/date-post-count.service';
import { PopularPostsService } from './popular-posts/popular-posts.service';
import { TagPostCountService } from './tag-post-count/tag-post-count.service';

import { RelativeTimePipe } from './post-header/relative-time.pipe';
import { PostComponent } from './post/post.component';
import { PostHeaderComponent } from './post-header/post-header.component';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    PostRoutingModule,
    MaterialModule,
    FlexLayoutModule
  ],
  declarations: [
    DatePostCountComponent,
    PopularPostsComponent,
    PostsComponent,
    RelativeTimePipe,
    TagPostCountComponent,
    PostComponent,
    PostHeaderComponent,
  ],
  exports: [
    DatePostCountComponent,
    PopularPostsComponent,
    TagPostCountComponent
  ],
  providers: [
    DatePostCountService,
    PopularPostsService,
    TagPostCountService
  ]
})
export class PostModule {
}
