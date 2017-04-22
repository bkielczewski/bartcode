import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentRoutingModule } from './document-routing.module';
import { DocumentComponent } from './document.component';
import { MetadataModule } from '../metadata/metadata.module';

@NgModule({
  imports: [
    CommonModule,
    DocumentRoutingModule,
    MetadataModule
  ],
  declarations: [
    DocumentComponent
  ]
})
export class DocumentModule {
}
