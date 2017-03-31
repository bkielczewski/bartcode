import { Component, OnInit } from '@angular/core';
import { TagPostCountService } from './tag-post-count.service';
import { TagPostCount } from './tag-post-count';

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
      .subscribe(tagCounts => this.tagCounts = tagCounts);
  }

  getFontSize(tagCount: TagPostCount) {
    return Math.max((tagCount.count / 5), 1);
  }

}
