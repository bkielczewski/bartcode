import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ArticleRoutingModule } from './article-routing.module';
import { ArticleComponent } from './article.component';
import { MetadataModule } from '../metadata/metadata.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    MetadataModule,
    ArticleRoutingModule
  ],
  declarations: [
    ArticleComponent
  ]
})
export class ArticleModule {
}
