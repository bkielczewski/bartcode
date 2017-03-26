import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-error',
  templateUrl: './error.component.html',
  styleUrls: ['./error.component.scss']
})
export class ErrorComponent implements OnInit {

  code: number;

  constructor(private route: ActivatedRoute) {
  }

  ngOnInit() {
    this.route.queryParams.map(params => params['code'] || null).subscribe(code => this.code = code);
  }

}
