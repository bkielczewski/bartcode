import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { Post } from '../post';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {
  post: Post;

  constructor(private route: ActivatedRoute,
              private sanitizer: DomSanitizer) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { post: Post }) => this.onPostLoaded(data.post));
  }

  onPostLoaded(post: Post) {
    post.textSafeHtml = this.sanitizer.bypassSecurityTrustHtml(post.text);
    this.post = post;
  }

}
