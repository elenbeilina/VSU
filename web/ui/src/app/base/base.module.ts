import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {IndexComponent} from './index/index.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { SignInComponent } from './sign-in/sign-in.component';
import {MaterialModule} from '../common/material.module';

const routes: Routes = [
  {
    path: '',
    component: IndexComponent
  }
];

@NgModule({
  declarations: [IndexComponent, FooterComponent, HeaderComponent, SignInComponent],
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
