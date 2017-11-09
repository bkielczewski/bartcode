import { SafeHtml } from '@angular/platform-browser';
import { Metadata } from '../../metadata/metadata';

export interface Article {
  relativeUrl: string;
  metadata: Metadata;
  body: string;
  bodySafeHtml: SafeHtml;
}
