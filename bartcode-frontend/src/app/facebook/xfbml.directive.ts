import { AfterViewInit, Directive, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

declare const FB: any;

@Directive({
  selector: '[appXfbml]'
})
export class XfbmlDirective implements AfterViewInit {

  constructor(@Inject(DOCUMENT) private document) {
  }

  ngAfterViewInit(): void {
    if (this.document && FB != null) {
      this.document.fbAsyncInit = FB.XFBML.parse();
    }
  }

}
