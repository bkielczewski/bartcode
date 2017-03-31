import { AfterViewInit, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Post } from '../post';
import { DomSanitizer } from '@angular/platform-browser';
import * as hljs from 'highlight.js';

declare const document;

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit, AfterViewInit {
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

  ngAfterViewInit(): void {
    if (document) {
      const blocks = document.querySelectorAll('pre code');
      Array(blocks).forEach.call(blocks, hljs.highlightBlock);
    }
  }

}
