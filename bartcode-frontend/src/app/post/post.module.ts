import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { MaterialModule } from '@angular/material';
import { FlexLayoutModule } from '@angular/flex-layout';

import { PostRoutingModule } from './post-routing.module';

import { DatesComponent } from './dates/dates.component';
import { PopularComponent } from './popular/popular.component';
import { PostsComponent } from './posts/posts.component';
import { TagsComponent } from './tags/tags.component';

import { DatePostCountService } from './dates/date-post-count.service';
import { PopularService } from './popular/popular.service';
import { TagPostCountService } from './tags/tag-post-count.service';

import { RelativeTimePipe } from './posts/relative-time.pipe';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    PostRoutingModule,
    MaterialModule,
    FlexLayoutModule
  ],
  declarations: [
    DatesComponent,
    PopularComponent,
    PostsComponent,
    RelativeTimePipe,
    TagsComponent
  ],
  exports: [
    DatesComponent,
    PopularComponent,
    TagsComponent
  ],
  providers: [
    DatePostCountService,
    PopularService,
    TagPostCountService
  ]
})
export class PostModule {
}
