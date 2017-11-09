import { Article } from '../../article/shared/article';
import { SafeHtml } from '@angular/platform-browser';

export interface Post extends Article {

  excerpt: string;
  excerptSafeHtml: SafeHtml;
  stats: Stats;
  published: string;
  tags: string[];

}

export interface Stats {
  shares: number;
  comments: number;
}
