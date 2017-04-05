import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { XfbmlDirective } from './xfbml.directive';

@NgModule({
  imports: [
    CommonModule
  ],
  declarations: [XfbmlDirective],
  exports: [XfbmlDirective]
})
export class FacebookModule {
}
