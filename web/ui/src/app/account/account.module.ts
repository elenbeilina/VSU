import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { SignInModalComponent } from './sign-in-modal/sign-in-modal.component';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: ProfileComponent
  }
];

@NgModule({
  declarations: [ProfileComponent, SignInModalComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ]
})
export class AccountModule { }
