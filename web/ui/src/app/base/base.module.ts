import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from './index/index.component';
import {FooterComponent} from './footer/footer.component';
import {HeaderComponent} from './header/header.component';
import {SignInComponent} from './sign-in/sign-in.component';
import {MaterialModule} from '../common/material.module';
import {MainComponent} from './main/main.component';

const routes: Routes = [
  {
    path: '',
    component: IndexComponent,
    children: [
      {
        path: '',
        component: MainComponent,
      },
      {
        path: 'news',
        loadChildren: () => import('../news/news.module').then(m => m.NewsModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('../account/account.module.js').then(m => m.AccountModule)
      },
      {
        path: 'sponsors',
        loadChildren: () => import('../sponsors/sponsors.module').then(m => m.SponsorsModule)
      },
      {
        path: 'questions',
        loadChildren: () => import('../faq/faq.module').then(m => m.FaqModule)
      }
    ]
  }
];

@NgModule({
  declarations: [IndexComponent, FooterComponent, HeaderComponent, SignInComponent, MainComponent],
  imports: [
    [RouterModule.forChild(routes)],
    CommonModule,
    MaterialModule
  ],
  entryComponents: [
    SignInComponent
  ],
})
export class BaseModule {
}
