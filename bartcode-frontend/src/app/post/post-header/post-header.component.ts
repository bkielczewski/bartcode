import { Component, Input, OnInit } from '@angular/core';
import { Post } from '../shared/post';

@Component({
  selector: 'app-post-header',
  templateUrl: './post-header.component.html',
  styleUrls: ['./post-header.component.scss']
})
export class PostHeaderComponent implements OnInit {

  @Input()
  post: Post;

  constructor() {
  }

  ngOnInit() {
  }

}
