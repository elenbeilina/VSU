import { NgModule } from '@angular/core';
import {MatIconModule} from '@angular/material/icon';
import {MatDialogModule} from '@angular/material/dialog';

const materialModules = [
  MatDialogModule,
  MatIconModule,
];

@NgModule({
  declarations: [],
  imports: [
    ...materialModules,
  ], exports: [
    ...materialModules
  ]
})
export class MaterialModule { }
