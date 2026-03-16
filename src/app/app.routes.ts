import { Routes } from '@angular/router';
import { Login } from './features/login/login';
import { ForgotPassword } from './features/forgot-password/forgot-password';
export const routes: Routes = [
  {
    path: 'login',
    component: Login,
  },
  {
    path: 'forgot-password',
    component: ForgotPassword,
  },
  {
    path: '',
    redirectTo: 'login',
    pathMatch: 'full',
  },
];
