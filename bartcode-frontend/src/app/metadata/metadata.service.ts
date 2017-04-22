import { Injectable } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';

@Injectable()
export class MetadataService {
  private indexTitle;
  private indexDescription: string;
  private indexUrl: string;

  constructor(private title: Title, private meta: Meta) {
    this.onInit();
  }

  private onInit() {
    this.indexTitle = this.title.getTitle();
    this.indexDescription = this.fromTagOrEmpty('name="description"');
    this.indexUrl = this.fromTagOrEmpty('property="og.url"');
  }

  private fromTagOrEmpty(selector: string) {
    const tag = this.meta.getTag(selector);
    return tag ? tag.content : '';
  }

  public updateMetadata(title?: string, description?: string, url?: string) {
    this.updateTitle(title);
    this.updateDescription(description ? description : this.indexDescription);
    this.updateUrl(url ? url : this.indexUrl);
  }

  private updateTitle(title: string) {
    let updatedTitle;
    if (title) {
      updatedTitle = title + ' ' + this.indexTitle.substring(this.indexTitle.indexOf('-'));
    } else {
      updatedTitle = this.indexTitle;
    }
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
