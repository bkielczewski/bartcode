import { RESPONSE } from '@nguniversal/express-engine/tokens'
import { Component, Inject, OnInit, Optional } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  code: number;

  constructor(private route: ActivatedRoute, @Optional() @Inject(RESPONSE) private response: any) {
  }

  ngOnInit() {
    this.route.params
      .map(params => params['code'])
      .subscribe(code => this.handleErrorCode(code));
  }

  private handleErrorCode(code: number) {
    this.code = code;
    if (this.response) {
      this.response.status(code);
    }
  }

}
