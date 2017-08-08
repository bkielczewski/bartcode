import { AfterViewInit, Directive, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';

declare const adsbygoogle: any;

@Directive({
  selector: '[appAdsense]'
})
export class AdsenseDirective implements AfterViewInit {

  constructor(@Inject(DOCUMENT) private document) {
  }

  ngAfterViewInit(): void {
    if (this.document && typeof adsbygoogle !== 'undefined') {
      const units = this.document.querySelectorAll('.adsbygoogle');
      for (let i = 0; i < units.length; i++) {
        try {
          adsbygoogle.push({});
        } catch (ignore) {
        }
      }
    }
  }

}
