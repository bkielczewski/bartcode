import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Document } from './shared/document';
import { MetadataService } from '../metadata/metadata.service';

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.scss']
})
export class DocumentComponent implements OnInit {

  document: Document;

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { document: Document }) => this.onDocumentLoaded(data.document));
  }

  private onDocumentLoaded(document: Document) {
    this.document = document;
    this.metadataService.updateMetadata(document.metadata);
  }

}
