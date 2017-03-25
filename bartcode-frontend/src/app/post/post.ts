export interface Post {

  id: number;
  stats: Stats;
  relativeUrl: string;
  published: string;
  tags: string[];
  metadata: Metadata;
  excerpt: string;

}

export interface Stats {
  shares: number;
  comments: number;
}

export interface Metadata {
  canonicalUrl: string;
  title: string;
  description: string;
}
