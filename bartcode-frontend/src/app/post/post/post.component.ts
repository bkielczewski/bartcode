import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Post } from '../shared/post';
import { MetadataService } from '../../metadata/metadata.service';

@Component({
  selector: 'app-post',
  templateUrl: './post.component.html',
  styleUrls: ['./post.component.scss']
})
export class PostComponent implements OnInit {

  post: Post;

  constructor(private route: ActivatedRoute,
              private metadataService: MetadataService) {
  }

  ngOnInit() {
    this.route.data.subscribe((data: { post: Post }) => this.onPostLoaded(data.post));
  }

  private onPostLoaded(post: Post) {
    this.post = post;
    this.metadataService.updateMetadata(post.metadata);
  }

}
