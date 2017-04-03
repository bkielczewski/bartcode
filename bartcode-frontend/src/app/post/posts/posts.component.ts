import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Post } from '../post';

declare const window: any;

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit, AfterViewInit {
  posts: Post[] = [];

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { posts: Post[] }) => this.posts = data.posts);
  }

  ngAfterViewInit(): void {
    if (window && window['adsbygoogle']) {
      const units = window.document.querySelectorAll('.adsbygoogle');
      for (let i = 0; i < units.length; i++) {
        window.adsbygoogle.push({});
      }
    }
  }

}
