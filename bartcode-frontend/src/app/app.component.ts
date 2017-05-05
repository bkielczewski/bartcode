import { Component, OnInit } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { MetadataService } from './metadata/metadata.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  constructor(private router: Router, private metadataService: MetadataService) {
  }

  ngOnInit(): void {
    this.router.events
      .filter(event => event instanceof NavigationStart)
      .subscribe((data) => this.metadataService.resetMetadata());
  }

}
