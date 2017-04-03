import { AfterViewInit, Component, OnInit } from '@angular/core';

declare const window: any;

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit, AfterViewInit {
  constructor() {
  }

  ngOnInit() {
  }

  ngAfterViewInit(): void {
    if (window && window['adsbygoogle']) {
      const units = window.document.querySelectorAll('.adsbygoogle');
      for (let i = 0; i < units.length; i++) {
        window.adsbygoogle.push({});
      }
    }
  }

}
