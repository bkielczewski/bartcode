import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HljsDirective } from './hljs.directive';

@NgModule({
  imports: [
    CommonModule,
  ],
  declarations: [
    HljsDirective
  ],
  exports: [
    HljsDirective
  ]
})
export class HljsModule {
}
