import { Component, OnInit } from '@angular/core';
import { PopularService } from './popular.service';
import { Post } from '../post';

@Component({
  selector: 'post-popular',
  templateUrl: './popular.component.html',
  styleUrls: ['./popular.component.scss']
})
export class PopularComponent implements OnInit {

  posts: Post[];

  constructor(private popularService: PopularService) {
  }

  ngOnInit() {
    this.popularService.getPopular()
      .toArray()
      .subscribe(posts => this.posts = posts);
  }

}
