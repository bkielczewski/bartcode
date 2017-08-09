import { AfterViewInit, Directive, Inject, NgZone } from '@angular/core';
import { DOCUMENT } from '@angular/common';

declare const adsbygoogle: any;

@Directive({
  selector: '[appAdsense]'
})
export class AdsenseDirective implements AfterViewInit {

  constructor(@Inject(DOCUMENT) private document, private zone: NgZone) {
  }

  ngAfterViewInit(): void {
    if (this.document && adsbygoogle) {
      const units = this.document.querySelectorAll('.adsbygoogle');
      this.zone.runOutsideAngular(() => this.requestAds(units.length));
    }
  }

  private requestAds(count: number) {
    for (let i = 0; i < count; i++) {
      try {
        adsbygoogle.push({});
      } catch (ignore) {
      }
    }
  }

}
