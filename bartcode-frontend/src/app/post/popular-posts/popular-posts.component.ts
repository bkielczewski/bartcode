import { Component, OnInit } from '@angular/core';

import { PopularPostsService } from './popular-posts.service';
import { Post } from '../post';

@Component({
  selector: 'app-popular-posts',
  templateUrl: './popular-posts.component.html',
  styleUrls: ['./popular-posts.component.scss']
})
export class PopularPostsComponent implements OnInit {

  posts: Post[];

  constructor(private popularService: PopularPostsService) {
  }

  ngOnInit() {
    this.popularService.getPopular()
      .toArray()
      .subscribe(posts => this.posts = posts);
  }

}
