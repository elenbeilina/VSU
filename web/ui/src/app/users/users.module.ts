import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {RouterModule, Routes} from '@angular/router';
import {AddUserModalComponent} from './users/add-user-modal/add-user-modal.component';
import {UsersComponent} from './users/users.component';
import {MatTableModule} from '@angular/material/table';
import {MaterialModule} from '../common/material.module';

const routes: Routes = [
  {
    path: '',
    component: UsersComponent
  }
];

@NgModule({
  declarations: [UsersComponent, AddUserModalComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    MatTableModule,
    MaterialModule
  ],
  entryComponents: [
    AddUserModalComponent
  ]
})
export class UsersModule { }
