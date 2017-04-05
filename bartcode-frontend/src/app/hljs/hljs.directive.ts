import { AfterViewInit, Directive, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';
import * as hljs from 'highlight.js';

@Directive({
  selector: '[appHljs]'
})
export class HljsDirective implements AfterViewInit {

  constructor(@Inject(DOCUMENT) private document) {
  }

  ngAfterViewInit(): void {
    if (this.document) {
      const blocks = this.document.querySelectorAll('pre code');
      Array(blocks).forEach.call(blocks, hljs.highlightBlock);
    }
  }

}
