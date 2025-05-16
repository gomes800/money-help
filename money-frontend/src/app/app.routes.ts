import { Routes } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { HomeComponent } from './pages/home/home.component';
import { ExpensesComponent } from './pages/expenses/expenses.component';
import { AddExpenseComponent } from './pages/add-expense/add-expense.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'dashboard', component: DashboardComponent },
    { path: 'expenses', component: ExpensesComponent},
    { path: 'addExpense', component: AddExpenseComponent},
];
