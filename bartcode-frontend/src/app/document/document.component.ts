import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Document } from './document';

@Component({
  selector: 'app-document',
  templateUrl: './document.component.html',
  styleUrls: ['./document.component.scss']
})
export class DocumentComponent implements OnInit {

  document: Document;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { document: Document }) => this.document = data.document);
  }

}
