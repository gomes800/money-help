import { Component, OnInit } from '@angular/core';
import { Expenses, ExpenseService, PagedResponse } from '../../services/expense.service';
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

  pagedExpenses: PagedResponse<Expenses> | null = null

  newExpense: Omit<Expenses, 'id'> = {
    name: '',
    description: '',
    amount: 0,
    category: '',
    date: ''
  }

  editingId: number | null = null;
  editExpense: Omit<Expenses, 'id'> = {
    name: '',
    category: '',
    description: '',
    amount: 0,
    date: ''
  };

  constructor(private expenseService: ExpenseService) { }

  ngOnInit(): void {
    this.expenseService.getMyExpenses().subscribe({
      next: (data) => {
        this.pagedExpenses = data;
        this.expenses = data.content;
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
        alert('Erro ao adicionar despesa.');
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

  startEdit(expense: Expenses): void {
    this.editingId = expense.id;
    this.editExpense = {
      name: expense.name,
      category: expense.category,
      description: expense.description,
      amount: expense.amount,
      date: expense.date
    };
  }

  cancelEdit(): void {
    this.editingId = null;
  }

  saveEdit(expenseId: number): void {
    const userId = 1;
    this.expenseService.updateExpense(expenseId, this.editExpense).subscribe({
      next: updated => {
        this.expenses = this.expenses.map(e =>
          e.id === expenseId ? updated : e
        );
        this.editingId = null;
      },
      error: err => console.error('Erro ao atualizar:', err)
    });
  }

  deleteExpense(expenseId: number): void {
    const confirmDelete = confirm('Tem certeza que deseja excluir essa despesa?');
    if (confirmDelete) {
      this.expenseService.deleteExpense(expenseId).subscribe({
        next: () => {
          this.expenses = this.expenses.filter(e => e.id !== expenseId);
        },
        error: err => {
          console.error('Erro ao excluir despesa:', err);
          alert('Erro ao excluir despesa.');
        }
      });
    }
  }

}
