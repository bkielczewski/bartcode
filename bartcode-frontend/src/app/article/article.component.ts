import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from './shared/article';
import { MetadataService } from '../metadata/metadata.service';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html'
})
export class ArticleComponent implements OnInit {

  article: Article;

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { article: Article }) => this.onLoaded(data.article));
  }

  private onLoaded(article: Article) {
    this.article = article;
    this.article.bodySafeHtml = this.sanitizer.bypassSecurityTrustHtml(article.body);
    this.metadataService.updateMetadata(article.metadata);
  }

}
