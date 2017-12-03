import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Article } from './shared/article';
import { MetadataService } from '../metadata/metadata.service';

@Component({
  selector: 'app-article',
  templateUrl: './article.component.html'
})
export class ArticleComponent implements OnInit {

  article: Article;

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { article: Article }) => this.onLoaded(data.article));
  }

  private onLoaded(article: Article) {
    this.article = article;
    this.metadataService.updateMetadata(article.metadata);
  }

}
