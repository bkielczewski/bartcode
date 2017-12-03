import { Component, OnInit } from '@angular/core';
import { TagPostCountService } from '../shared/tag-post-count.service';
import { TagPostCount } from '../shared/tag-post-count';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-tag-post-count',
  templateUrl: './tag-post-count.component.html',
  styleUrls: ['./tag-post-count.component.scss']
})
export class TagPostCountComponent implements OnInit {

  private static MAX_FONT_SIZE_REM = 2.8;
  private static MIN_FONT_SIZE_REM = .8;

  tagCounts: TagPostCount[];

  private deltaCount: number;

  constructor(private tagCountService: TagPostCountService) {
  }

  ngOnInit() {
    this.tagCountService.getTagCounts()
      .toArray()
      .catch(() => <Observable<TagPostCount[]>> Observable.empty())
      .subscribe(tagCounts => this.refreshTagCounts(tagCounts));
  }

  getFontSizeRem(tagCount: TagPostCount) {
    const rem = TagPostCountComponent.MAX_FONT_SIZE_REM * (tagCount.count / this.deltaCount);
    return Math.max(Math.min(rem, TagPostCountComponent.MAX_FONT_SIZE_REM), TagPostCountComponent.MIN_FONT_SIZE_REM);
  }

  private refreshTagCounts(tagCounts) {
    this.tagCounts = tagCounts;
    const minCount = this.tagCounts.map(tagCount => tagCount.count).reduce((p, c) => p < c ? p : c);
    const maxCount = this.tagCounts.map(tagCount => tagCount.count).reduce((p, c) => p > c ? p : c);
    this.deltaCount = maxCount - minCount;
  }

}
