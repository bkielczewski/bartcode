import { Component, HostListener, Input } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {

  @Input()
  mainPage: boolean = true;
  fullHeader: boolean = true;

  @HostListener('window:scroll', [])
  onWindowScroll() {
    const number = window.scrollY;
    if (number > 0) {
      this.fullHeader = false;
    } else {
      this.fullHeader = true;
    }
  }

}
