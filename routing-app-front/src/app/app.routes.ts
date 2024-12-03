import { Routes } from '@angular/router';

export const routes: Routes = [
  {
    path: 'messages',
    loadComponent: () => import('./messages/messages.component')
      .then(m => m.MessagesComponent)
  },
  {
    path: 'partners',
    loadComponent: () => import('./partners/partners.component')
      .then(m => m.PartnersComponent)
  },
  {
    path: '',
    redirectTo: 'partners',
    pathMatch: 'full'
  }
];
