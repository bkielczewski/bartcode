import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatButtonModule } from '@angular/material';
import { MetadataModule } from '../metadata/metadata.module';
import { AdsenseModule } from '../adsense/adsense.module';
import { FacebookModule } from '../facebook/facebook.module';
import { HljsModule } from '../hljs/hljs.module';
import { PostRoutingModule } from './post-routing.module';
import { DatePostCountComponent } from './date-post-count/date-post-count.component';
import { PopularPostsComponent } from './popular-posts/popular-posts.component';
import { PostsComponent } from './posts/posts.component';
import { TagPostCountComponent } from './tag-post-count/tag-post-count.component';
import { DatePostCountService } from './shared/date-post-count.service';
import { PopularPostsService } from './shared/popular-posts.service';
import { TagPostCountService } from './shared/tag-post-count.service';
import { RelativeTimePipe } from './shared/relative-time.pipe';
import { PostComponent } from './post/post.component';
import { PostHeaderComponent } from './post-header/post-header.component';
import { HttpClientModule } from '@angular/common/http';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    MatButtonModule,
    MetadataModule,
    AdsenseModule,
    HljsModule,
    FacebookModule,
    SharedModule,
    PostRoutingModule
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
