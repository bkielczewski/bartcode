import { AfterViewInit, Directive, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/platform-browser';

declare const window: any;
declare const FB: any;

@Directive({
  selector: '[appXfbml]'
})
export class XfbmlDirective implements AfterViewInit {

  constructor(@Inject(DOCUMENT) private document) {
  }

  ngAfterViewInit(): void {
    if (this.document && window && FB) {
      window.fbAsyncInit = FB.XFBML.parse();
    }
  }

}
