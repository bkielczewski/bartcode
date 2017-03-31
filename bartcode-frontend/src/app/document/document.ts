import { SafeHtml } from '@angular/platform-browser';

export interface Document {

  relativeUrl: string;
  metadata: Metadata;
  text: string;
  textSafeHtml: SafeHtml;

}

export interface Metadata {

  canonicalUrl: string;
  title: string;
  description: string;

}
