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
    if (this.document && adsbygoogle != null) {
      const units = this.document.querySelectorAll('.adsbygoogle');
      this.zone.runOutsideAngular(() => setTimeout(() => this.requestAds(units.length), 500));
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
