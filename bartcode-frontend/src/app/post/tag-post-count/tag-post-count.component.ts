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

  tagCounts: TagPostCount[];

  constructor(private tagCountService: TagPostCountService) {
  }

  ngOnInit() {
    this.tagCountService.getTagCounts()
      .toArray()
      .catch(() => <Observable<TagPostCount[]>> Observable.empty())
      .subscribe(tagCounts => this.tagCounts = tagCounts);
  }

  getFontSize(tagCount: TagPostCount) {
    return Math.max((tagCount.count / 5), 1);
  }

}
