import { Component, OnInit } from '@angular/core';
import { TagPostCountService } from './tag-post-count.service';
import { TagPostCount } from './tag-post-count';

@Component({
  selector: 'post-tags',
  templateUrl: './tags.component.html',
  styleUrls: ['./tags.component.scss']
})
export class TagsComponent implements OnInit {

  tagCounts: TagPostCount[];

  constructor(private tagCountService: TagPostCountService) {
  }

  ngOnInit() {
    this.tagCountService.getTagCounts()
      .toArray()
      .subscribe(tagCounts => this.tagCounts = tagCounts);
  }

  getFontSize(tagCount: TagPostCount) {
    return Math.max((tagCount.count / 5), 1);
  }

}
