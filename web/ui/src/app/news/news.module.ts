import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {NewsComponent} from './news/news.component';
import {RouterModule, Routes} from '@angular/router';
import {MaterialModule} from '../common/material.module';
import {MatIconModule} from '@angular/material/icon';
import {MatTooltipModule} from '@angular/material/tooltip';
import { AddNewsModalComponent } from './add-news-modal/add-news-modal.component';

const routes: Routes = [
  {
    path: '',
    component: NewsComponent,
  }
];

@NgModule({
  declarations: [NewsComponent, AddNewsModalComponent],
  imports: [
    [RouterModule.forChild(routes)],
    CommonModule,
    MatIconModule,
    MatTooltipModule,
  ],
  entryComponents: [
    AddNewsModalComponent
  ]
})
export class NewsModule {
}
