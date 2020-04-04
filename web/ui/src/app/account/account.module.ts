import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProfileComponent } from './profile/profile.component';
import { SignInModalComponent } from './sign-in-modal/sign-in-modal.component';



@NgModule({
  declarations: [ProfileComponent, SignInModalComponent],
  imports: [
    CommonModule
  ]
})
export class AccountModule { }
