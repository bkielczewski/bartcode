export interface Document {
  relativeUrl: string;
  metadata: Metadata;
  text: string;
}

export interface Metadata {
  canonicalUrl: string;
  title: string;
  description: string;
}
