import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpModule } from '@angular/http';
import { PopularComponent } from './popular/popular.component';
import { DatesComponent } from './dates/dates.component';
import { TagsComponent } from './tags/tags.component';
import { TagPostCountService } from './tags/tag-post-count.service';
import { PopularService } from './popular/popular.service';
import { DatePostCountService } from './dates/date-post-count.service';
import { PostRoutingModule } from './post-routing.module';

@NgModule({
  imports: [
    CommonModule,
    HttpModule,
    PostRoutingModule
  ],
  declarations: [
    DatesComponent,
    PopularComponent,
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
