import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Post } from '../shared/post';
import { MetadataService } from '../../metadata/metadata.service';
import { HalUtils, Resources } from '../../shared/spring-data-rest';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  year?: number;
  month?: number;
  tag?: string;
  posts: Post[] = [];
  nextPageUrl?: string;
  previousPageUrl?: string;

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService,
              private sanitizer: DomSanitizer,
              @Inject(LOCALE_ID) private locale: string) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: RouteData) => this.onUpdate(data));
    this.route.params.subscribe((params: Params) => this.onParamsUpdate(params));
  }

  private onUpdate(data: RouteData) {
    this.posts = HalUtils.getEmbedded('posts', data.posts);
    this.updatePagingUrls(data);
  }

  private onParamsUpdate(params: Params) {
    this.year = params['year'];
    this.month = params['month'];
    this.tag = params['tag'];
    this.updateMetadata(params);
  }

  private updatePagingUrls(data: RouteData) {
    const pageNumber = data.posts.page.number + 1;
    if (pageNumber < (data.posts.page.totalPages)) {
      this.nextPageUrl = data.pagingUrl + '/page/' + (pageNumber + 1);
    } else {
      this.nextPageUrl = undefined;
    }
    if (pageNumber > 1) {
      this.previousPageUrl = data.pagingUrl + '/page/' + (pageNumber - 1);
    } else {
      this.previousPageUrl = undefined;
    }
  }

  private updateMetadata(params: Params) {
    let title;
    if (params['year'] && params['month']) {
      title = 'Posts from ' + this.getMonthName(params['month']) + ' ' + params['year'];
    } else if (params['year']) {
      title = 'Posts from ' + params['year'];
    } else if (params['tag']) {
      title = 'Posts about ' + params['tag'];
    } else {
      title = 'Blog';
    }
    if (params['page']) {
      title += ', page #' + params['page']
    }
    this.metadataService.updateMetadata(title);
  }

  private getMonthName(month: string): string {
    return new Date('1900-' + month + '-01').toLocaleString(this.locale, { month: 'long' });
  }

}

interface RouteData {
  posts: Resources<Post>,
  pagingUrl: string
}
