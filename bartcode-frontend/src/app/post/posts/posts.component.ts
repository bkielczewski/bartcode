import { Component, Inject, LOCALE_ID, OnInit } from '@angular/core';
import { ActivatedRoute, Params } from '@angular/router';
import { Post } from '../shared/post';
import { MetadataService } from '../../metadata/metadata.service';
import { HalUtils, Resources } from '../../shared/spring-data-rest';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.scss']
})
export class PostsComponent implements OnInit {

  year?: number;
  month?: number;
  tag?: string;
  posts: Post[] = [];
  nextPageUrl?: string;
  previousPageUrl?: string;

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService,
              @Inject(LOCALE_ID) private locale: string) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: RouteData) => this.onUpdate(data));
    this.route.params.subscribe((params: Params) => this.onParamsUpdate(params));
  }

  private onUpdate(data: RouteData) {
    this.posts = HalUtils.getEmbedded('posts', data.posts);
    this.updatePagingUrls(data);
  }

  getTitle(): string {
    if (!this.year && !this.month && !this.tag) {
      return 'All Articles';
    } else if (this.year && !this.month && !this.tag) {
      return `Articles of ${this.year}`;
    } else if (this.year && this.month && !this.tag) {
      return `Articles of ${this.getMonthName(this.month)} ${this.year}`;
    } else if (!this.year && !this.month && this.tag) {
      return `Articles on ${this.tag}`;
    }
  }

  private updatePagingUrls(data: RouteData) {
    const pageNumber = data.posts.page.number + 1;
    if (pageNumber < (data.posts.page.totalPages)) {
      this.nextPageUrl = data.pagingUrl + '/page/' + (pageNumber + 1);
    } else {
      this.nextPageUrl = undefined;
    }
    if (pageNumber > 1) {
      this.previousPageUrl = data.pagingUrl + '/page/' + (pageNumber - 1);
    } else {
      this.previousPageUrl = undefined;
    }
  }

  private onParamsUpdate(params: Params) {
    this.year = params['year'];
    this.month = params['month'];
    this.tag = params['tag'];
    this.updateMetadata();
  }

  private updateMetadata() {
    const title = this.getTitle();
    const description = this.getDescription();
    this.metadataService.updateMetadata({ title: title, description: description });
  }

  private getMonthName(month: number): string {
    return new Date(`1900-${month}-01`).toLocaleString(this.locale, { month: 'long' });
  }

  private getDescription(): string {
    if (!this.year && !this.month && !this.tag) {
      return 'List of all available articles published on Bartcode';
    } else if (this.year && !this.month && !this.tag) {
      return `List of articles published in the year of ${this.year} published on Bartcode`;
    } else if (this.year && this.month && !this.tag) {
      return `List of articles published on ${this.getMonthName(this.month)} ${this.year} published on Bartcode`;
    } else if (!this.year && !this.month && this.tag) {
      return `List of articles covering ${this.tag} published on Bartcode`;
    }
  }

}

interface RouteData {
  posts: Resources<Post>,
  pagingUrl: string
}
