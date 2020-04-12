import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';


const routes: Routes = [
  {
    path: '',
    loadChildren: () => import('./base/base.module').then(m => m.BaseModule)
  }
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {useHash: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
