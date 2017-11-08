import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DocumentRoutingModule } from './document-routing.module';
import { DocumentComponent } from './document.component';
import { MetadataModule } from '../metadata/metadata.module';
import { HttpClientModule } from '@angular/common/http';

@NgModule({
  imports: [
    CommonModule,
    HttpClientModule,
    MetadataModule,
    DocumentRoutingModule
  ],
  declarations: [
    DocumentComponent
  ]
})
export class DocumentModule {
}
