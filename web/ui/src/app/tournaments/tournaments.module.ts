import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TournamentsComponent } from './tournaments/tournaments.component';
import {RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: TournamentsComponent
  }
]

@NgModule({
  declarations: [TournamentsComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ]
})
export class TournamentsModule { }
