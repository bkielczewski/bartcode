import { Injectable } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

@Injectable()
export class MetadataService {
  private indexTitle;
  private indexDescription: HTMLMetaElement;
  private indexUrl: HTMLMetaElement;

  constructor(private title: Title, private meta: Meta) {
    this.onInit();
  }

  private onInit() {
    this.indexTitle = this.title.getTitle();
    this.indexDescription = this.meta.getTag('name="description"');
    this.indexUrl = this.meta.getTag('property="og.url"');
  }

  public updateMetadata(title?: string, description?: string, url?: string) {
    this.updateTitle(title);
    this.updateDescription(description ? description : this.indexDescription ? this.indexDescription.content : '');
    this.updateUrl(url ? url : this.indexUrl ? this.indexUrl.content : '');
  }

  private updateTitle(title: string) {
    const updatedTitle = this.indexTitle + (title ? ' - ' + title : '');
    this.title.setTitle(updatedTitle);
    this.meta.updateTag({ content: updatedTitle }, 'property="og:title"');
  }

  private updateDescription(description: string) {
    this.meta.updateTag({ content: description }, 'name="description"');
    this.meta.updateTag({ content: description }, 'property="og:description"');
  }

  private updateUrl(url: string) {
    this.meta.updateTag({ content: url }, 'property="og:url"');
  }

}
