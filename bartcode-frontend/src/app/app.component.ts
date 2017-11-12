import { Component, Inject, OnInit, PLATFORM_ID } from '@angular/core';
import { NavigationEnd, Router } from '@angular/router';
import { MetadataService } from './metadata/metadata.service';
import { isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html'
})
export class AppComponent implements OnInit {

  mainPage = true;

  constructor(private router: Router,
              private metadataService: MetadataService,
              @Inject(PLATFORM_ID) private platformId: Object) {
  }

  ngOnInit(): void {
    this.updateMainPageStatus();
    this.router.events
      .filter(event => event instanceof NavigationEnd)
      .subscribe(() => this.onNavigation());
  }

  private onNavigation() {
    this.updateMainPageStatus();
    this.resetMetadata();
    this.scrollIntoViewOnBrowser();
  }

  private updateMainPageStatus() {
    this.mainPage = this.router.url == '/';
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

interface RouteData {
  mainPage?: boolean;
}
