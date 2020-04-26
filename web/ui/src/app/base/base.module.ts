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
        path: 'sponsors',
        loadChildren: () => import('../sponsors/sponsors.module').then(m => m.SponsorsModule)
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
