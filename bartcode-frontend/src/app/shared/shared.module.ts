import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { SafeHtmlPipe } from './safe-html.pipe';

@NgModule({
  imports: [
    BrowserModule
  ],
  declarations: [
    SafeHtmlPipe
  ],
  exports: [
    SafeHtmlPipe
  ]
})
export class SharedModule {
}
