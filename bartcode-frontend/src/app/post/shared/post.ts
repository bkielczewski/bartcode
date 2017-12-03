import { Article } from '../../article/shared/article';

export interface Post extends Article {

  excerpt: string;
  stats: Stats;
  published: string;
  tags: string[];

}

export interface Stats {
  shares: number;
  comments: number;
}
