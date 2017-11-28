import { NgModule } from '@angular/core';
import { ErrorRoutingModule } from './error-routing.module';
import { ErrorComponent } from './error.component';
import { CommonModule } from '@angular/common';
import { ErrorHttpInterceptor } from './error-http-interceptor';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { MatIconModule } from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    MatIconModule,
    ErrorRoutingModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: ErrorHttpInterceptor,
      multi: true,
    }
  ],
  declarations: [
    ErrorComponent
  ]
})
export class ErrorModule {
}
