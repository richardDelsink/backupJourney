import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './components/login/login.component';
import { SearchComponent } from './components/search/search.component';
import { StepComponent } from './components/step/step.component';
import {VerifyComponent} from './components/verify/verify.component';
import {RegisterComponent} from './components/register/register.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'search', component: SearchComponent},
  { path: 'step', component: StepComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'verify:key', component: VerifyComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
