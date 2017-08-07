import { Document } from '../../document/shared/document';

export interface Post extends Document {

  excerpt: string;
  stats: Stats;
  published: string;
  tags: string[];

}

export interface Stats {
  shares: number;
  comments: number;
}
