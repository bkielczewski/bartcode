import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Post } from '../post';
import { MetadataService } from '../../metadata/metadata.service';
import { Resources } from '../../spring-data-rest';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts: Post[] = [];
  nextPageUrl: string;
  previousPageUrl: string;

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService,
              @Inject(LOCALE_ID) private locale: string) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: RouteData) => this.onUpdate(data));
    this.route.params.subscribe((params: Params) => this.onParamsUpdate(params));
  }

  private onUpdate(data: RouteData) {
    this.posts = data.posts._embedded.posts;
    this.updatePagingUrls(data);
  }

  private onParamsUpdate(params: Params) {
    this.updateMetadata(params);
  }

  private updatePagingUrls(data: RouteData) {
    const pageNumber = data.posts.page.number + 1;
    if (pageNumber < (data.posts.page.totalPages)) {
      this.nextPageUrl = data.pagingUrl + '/page/' + (pageNumber + 1);
    } else {
      this.nextPageUrl = null;
    }
    if (pageNumber > 1) {
      this.previousPageUrl = data.pagingUrl + '/page/' + (pageNumber - 1);
    } else {
      this.previousPageUrl = null;
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
