import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { MdButtonModule, MdToolbarModule } from '@angular/material';

import { AppComponent } from './app.component';
import { ErrorComponent } from './error/error.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';

import { PostModule } from './post/post.module';
import { AppRoutingModule } from './app-routing.module';
import { DocumentModule } from './document/document.module';
import { AdsenseModule } from './adsense/adsense.module';

@NgModule({
  declarations: [
    AppComponent,
    ErrorComponent,
    FooterComponent,
    HomeComponent,
    NavbarComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    HttpModule,
    FlexLayoutModule,
    MdToolbarModule,
    MdButtonModule,
    AppRoutingModule,
    PostModule,
    DocumentModule,
    AdsenseModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: "en-GB" }
  ],
  exports: [
    AppComponent
  ]
})
export class AppModule {
}
