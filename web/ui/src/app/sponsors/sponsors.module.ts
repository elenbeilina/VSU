import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SponsorsComponent} from './sponsors/sponsors.component';
import {RouterModule, Routes} from '@angular/router';


const routes: Routes = [
  {
    path: '',
    component: SponsorsComponent
  }
];

@NgModule({
  declarations: [SponsorsComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ]
})
export class SponsorsModule {
}
