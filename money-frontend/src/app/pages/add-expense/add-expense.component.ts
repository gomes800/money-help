import { Component } from '@angular/core';
import { Expenses, ExpenseService } from '../../services/expense.service';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';



@Component({
  selector: 'app-add-expense',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './add-expense.component.html',
  styleUrl: './add-expense.component.css'
})
export class AddExpenseComponent {

  newExpense: Omit<Expenses, 'id'> = {
    name: '',
    description: '',
    amount: 0,
    category: '',
    date: ''
  }
  
  constructor(private expenseService: ExpenseService) {}

  onSubmit(): void {
    this.expenseService.addExpense(this.newExpense).subscribe({
      next: () => {
        this.resetForm();
      },
      error: (err) => {
        console.error('Erro ao adicionar despesa: ', err);
      }
    });
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

  cancel(): void {
    this.resetForm();
  }

}
