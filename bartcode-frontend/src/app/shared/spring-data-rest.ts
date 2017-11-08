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

export class HalUtils {

  static getEmbedded<T>(name: string, resources: Resources<T>): T[] {
    return resources._embedded ? resources._embedded[name] : [];
  }

}
