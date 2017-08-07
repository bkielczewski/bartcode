import { SafeHtml } from '@angular/platform-browser';
import { Metadata } from '../../metadata/metadata';

export interface Document {

  relativeUrl: string;
  metadata: Metadata;
  text: string;
  textSafeHtml: SafeHtml;

}
