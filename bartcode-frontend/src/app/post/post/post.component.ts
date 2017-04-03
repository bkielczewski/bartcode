import { AfterViewInit, Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';

import { Post } from '../post';
import * as hljs from 'highlight.js';

declare const window: any;

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
    if (window) {
      if (window['hljs']) {
        const blocks = window.document.querySelectorAll('pre code');
        Array(blocks).forEach.call(blocks, window.hljs.highlightBlock);
      }
      if (window['FB']) {
        window.FB.XFBML.parse();
      }
      if (window && window['adsbygoogle']) {
        const units = window.document.querySelectorAll('.adsbygoogle');
        for (let i = 0; i < units.length; i++) {
          window.adsbygoogle.push({});
        }
      }
    }
  }

}
