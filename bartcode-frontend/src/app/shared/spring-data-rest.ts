export interface Resources<T> {
  _embedded: { [name: string]: T[] };
  _links: { [name: string]: any };
  page: Page;
}

export interface Page {
  size: number;
  totalElements: number;
  totalPages: number;
  number: number;
}
