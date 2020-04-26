import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { QuestionsComponent } from './questions/questions.component';
import {Router, RouterModule, Routes} from '@angular/router';

const routes: Routes = [
  {
    path: '',
    component: QuestionsComponent
  }
]

@NgModule({
  declarations: [QuestionsComponent],
  imports: [
    RouterModule.forChild(routes),
    CommonModule
  ]
})
export class FaqModule { }
