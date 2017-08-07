import { Component, OnInit } from '@angular/core';

import { PopularPostsService } from '../shared/popular-posts.service';
import { Post } from '../shared/post';
import { Observable } from 'rxjs/Observable';

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
      .catch(() => <Observable<Post[]>>Observable.empty())
      .subscribe(posts => this.posts = posts);
  }

}
