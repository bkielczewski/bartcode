import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';

import { Post } from '../post';
import { MetadataService } from '../../metadata/metadata.service';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {
  posts: Post[] = [];

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService,
              @Inject(LOCALE_ID) private locale: string) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { posts: Post[] }) => this.posts = data.posts);
    this.route.params.subscribe((params: Params) => this.updateMetadata(params));
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
    this.metadataService.updateMetadata(title);
  }

  private getMonthName(month: string): string {
    return new Date('1900-' + month + '-01').toLocaleString(this.locale, { month: 'long' });
  }

}
