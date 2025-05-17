import { Component, OnInit } from '@angular/core';
import { Expenses, ExpenseService } from '../../services/expense.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-expenses',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './expenses.component.html',
  styleUrl: './expenses.component.css'
})
export class ExpensesComponent implements OnInit {

  expenses: Expenses[] = [];

  newExpense: Omit<Expenses, 'id'> = {
    name: '',
    description: '',
    amount: 0,
    category: '',
    date: ''
  }

  constructor(private expenseService: ExpenseService) {}

  ngOnInit(): void {
    this.expenseService.getMyExpenses().subscribe({
      next: (data) => {
        this.expenses = data;
      },
      error: (err) => {
        console.error("Erro ao carregar despesas: ", err);
      }
    });
  }

  onSubmit(): void {
    this.expenseService.addExpense(this.newExpense).subscribe({
      next: (newExpense) => {
        this.expenses?.push(newExpense);
        this.resetForm();
      },
      error: (err) => {
        console.error('Erro ao adicionar despesa:', err);
      }
    })
  }

  resetForm(): void {
  this.newExpense = {
    name: '',
    description: '',
    amount: 0,
    category: '',
    date: ''
  };
}

}
