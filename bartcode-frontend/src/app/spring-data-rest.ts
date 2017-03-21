export interface Resources<T> {

  _embedded: { [name: string]: T[] };
  _links: { [name: string]: any }

}
