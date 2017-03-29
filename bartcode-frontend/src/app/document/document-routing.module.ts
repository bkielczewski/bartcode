import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DocumentService } from './document.service';
import { DocumentComponent } from './document.component';
import { DocumentResolver } from './document.resolver';

const routes: Routes = [
  {
    path: 'privacy-and-cookies',
    component: DocumentComponent,
    pathMatch: 'full',
    resolve: { document: DocumentResolver }
  },
  { path: 'about', component: DocumentComponent, pathMatch: 'full', resolve: { document: DocumentResolver } }
];

@NgModule({
  imports: [
    RouterModule.forChild(routes)
  ],
  exports: [
    RouterModule
  ],
  providers: [
    DocumentService,
    DocumentResolver
  ]
})
export class DocumentRoutingModule {
}
