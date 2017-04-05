import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AdsenseDirective } from './adsense.directive';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [AdsenseDirective],
  exports: [AdsenseDirective]
})
export class AdsenseModule {
}
