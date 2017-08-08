import { AfterViewInit, Directive, Inject, NgZone } from '@angular/core';
import { DOCUMENT } from '@angular/common';
import * as hljs from 'highlight.js';

@Directive({
  selector: '[appHljs]'
})
export class HljsDirective implements AfterViewInit {

  constructor(@Inject(DOCUMENT) private document, private zone: NgZone) {
  }

  ngAfterViewInit(): void {
    if (this.document) {
      const blocks = this.document.querySelectorAll('pre code');
      this.zone.runOutsideAngular(() => Array(blocks).forEach.call(blocks, hljs.highlightBlock));
    }
  }

}
