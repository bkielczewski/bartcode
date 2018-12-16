import { RESPONSE } from '@nguniversal/express-engine/tokens'
import { Component, Inject, OnInit, Optional, PLATFORM_ID } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { isPlatformServer } from '@angular/common';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  code: number;

  constructor(@Inject(PLATFORM_ID) private platformId: Object,
              private route: ActivatedRoute,
              @Optional() @Inject(RESPONSE) private response: any) {
  }

  ngOnInit() {
    this.code = this.route.snapshot.params['code'];
    if (isPlatformServer(this.platformId)) {
      this.response.status(this.code);
    }
  }

}
