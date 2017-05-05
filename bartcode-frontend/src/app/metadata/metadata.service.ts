import { Injectable } from '@angular/core';
import { Meta, Title } from '@angular/platform-browser';
import { Metadata } from './metadata';

@Injectable()
export class MetadataService {

  private title;
  private description: string;
  private canonicalUrl: string;

  constructor(private titleService: Title, private metaService: Meta) {
    this.onInit();
  }

  private onInit() {
    this.title = this.titleService.getTitle();
    this.description = this.fromTagOrEmpty('name="description"');
    this.canonicalUrl = this.fromTagOrEmpty('property="og.url"');
  }

  private fromTagOrEmpty(selector: string) {
    const tag = this.metaService.getTag(selector);
    return tag ? tag.content : '';
  }

  public resetMetadata() {
    this.updateTitle(this.title);
    this.updateDescription(this.description);
    this.updateCanonicalUrl(this.canonicalUrl);
  }

  public updateMetadata(metadata: Metadata) {
    this.updateTitle(metadata.title);
    this.updateDescription(metadata.description ? metadata.description : this.description);
    this.updateCanonicalUrl(metadata.canonicalUrl ? metadata.canonicalUrl : this.canonicalUrl);
  }

  private updateTitle(title: string) {
    let updatedTitle;
    if (title) {
      updatedTitle = title + ' ' + this.title.substring(this.title.indexOf('-'));
    } else {
      updatedTitle = this.title;
    }
    this.titleService.setTitle(updatedTitle);
    this.metaService.updateTag({ content: updatedTitle }, 'property="og:title"');
  }

  private updateDescription(description: string) {
    this.metaService.updateTag({ content: description }, 'name="description"');
    this.metaService.updateTag({ content: description }, 'property="og:description"');
  }

  private updateCanonicalUrl(url: string) {
    this.metaService.updateTag({ content: url }, 'property="og:url"');
  }

}
