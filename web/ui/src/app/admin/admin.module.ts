import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {UsersComponent} from './users/users.component';
import {RouterModule, Routes} from '@angular/router';
import {HeaderAdminComponent} from './base-admin/header-admin/header-admin.component';
import {FooterAdminComponent} from './base-admin/footer-admin/footer-admin.component';
import {IndexAdminComponent} from './base-admin/index-admin/index-admin.component';
import {MaterialModule} from '../common/material.module';
import {MatTableModule} from '@angular/material/table';
import {MatTooltipModule} from '@angular/material/tooltip';
import { AddUserModalComponent } from './users/add-user-modal/add-user-modal.component';

const routes: Routes = [
  {
    path: '',
    component: IndexAdminComponent,
    children: [{
      path: '',
      redirectTo: '/admin/users',
      pathMatch: 'full'
    },
      {
        path: 'users',
        component: UsersComponent,
      }]
  },

];

@NgModule({
  declarations: [UsersComponent, HeaderAdminComponent, FooterAdminComponent, IndexAdminComponent, AddUserModalComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule,
    MaterialModule,
    MatTableModule,
    MatTooltipModule
  ],
  entryComponents: [AddUserModalComponent]
})
export class AdminModule {
}
