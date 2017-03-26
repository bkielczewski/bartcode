import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentRoutingModule } from './document-routing.module';
import { DocumentComponent } from './document.component';

@NgModule({
  imports: [
    CommonModule,
    DocumentRoutingModule
  ],
  declarations: [
    DocumentComponent
  ]
})
export class DocumentModule {
}
