import { LOCALE_ID, NgModule } from '@angular/core';
import { CommonModule, registerLocaleData } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatButtonModule, MatIconModule, MatToolbarModule } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent } from './app.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { NavbarComponent } from './navbar/navbar.component';
import { PostModule } from './post/post.module';
import { AppRoutingModule } from './app-routing.module';
import { ArticleModule } from './article/article.module';
import { AdsenseModule } from './adsense/adsense.module';
import { HttpClientModule } from '@angular/common/http';
import { ErrorModule } from './error/error.module';
import { ServiceWorkerModule } from '@angular/service-worker';
import locale from '@angular/common/locales/en-GB';
import { environment } from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    FooterComponent,
    HomeComponent,
    NavbarComponent
  ],
  imports: [
    BrowserModule.withServerTransition({ appId: 'bartcode-frontend' }),
    BrowserAnimationsModule,
    ServiceWorkerModule.register('/ngsw-worker.js', { enabled: environment.production }),
    CommonModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatIconModule,
    MatToolbarModule,
    AdsenseModule,
    ArticleModule,
    ErrorModule,
    PostModule,
    AppRoutingModule
  ],
  providers: [
    { provide: LOCALE_ID, useValue: "en-GB" }
  ],
  bootstrap: [
    AppComponent
  ]
})
export class AppModule {

  constructor() {
    registerLocaleData(locale);
  }

}
