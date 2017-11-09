import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { NavigationStart, Router } from '@angular/router';
import { MetadataService } from './metadata/metadata.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  constructor(private router: Router,
              private metadataService: MetadataService,
              @Inject(PLATFORM_ID) private platformId: Object) {
  }

  ngOnInit(): void {
    this.router.events
      .filter(event => event instanceof NavigationStart)
      .subscribe(() => this.onNavigationStart());
  }

  private onNavigationStart() {
    this.resetMetadata();
    this.scrollIntoViewOnBrowser();
  }

  private resetMetadata() {
    this.metadataService.resetMetadata();
  }

  private scrollIntoViewOnBrowser() {
    if (isPlatformBrowser(this.platformId)) {
      window.scroll(0, 0);
    }
  }
}
