import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { HomeComponent } from './pages/home/home.component';
import { ExpensesComponent } from './pages/expenses/expenses.component';
import { AddExpenseComponent } from './pages/add-expense/add-expense.component';
import { AddBalanceComponent } from './pages/add-balance/add-balance.component';
import { RegisterComponent } from './pages/register/register.component';
import { LoginComponent } from './pages/login/login.component';

export const routes: Routes = [
    { path: '', component: LoginComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'expenses', component: ExpensesComponent},
    { path: 'addExpense', component: AddExpenseComponent},
    { path: 'addBalance', component: AddBalanceComponent},
    { path: 'register', component: RegisterComponent },
    { path: 'login', component: LoginComponent },
];
