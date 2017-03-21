export interface Post {

  id: number;
  stats: Stats;
  slug: string;
  published: string;
  tags: string[];
  title: string;
  excerpt: string;

}

export interface Stats {
  shares: number;
  comments: number;
}
