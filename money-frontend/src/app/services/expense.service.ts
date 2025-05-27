import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Expenses {
  id: number;
  name: string;
  category: string;
  description: string;
  amount: number;
  date: string;
}

export interface PagedResponse<T> {
  content: T[];
  pageNumber: number;
  pageSize: number;
  totalElements: number;
  totalPages: number;
  last: boolean;
}

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private readonly baseUrl= `${environment.apiUrl}/expenses`

  constructor(private http: HttpClient) { }

  getMyExpenses(): Observable<PagedResponse<Expenses>> {
    return this.http.get<PagedResponse<Expenses>>(`${this.baseUrl}/user`);
  }

  addExpense(expense: Omit<Expenses, 'id'>): Observable<Expenses> {
    return this.http.post<Expenses>(`${this.baseUrl}/insert`, expense);
  }

  updateExpense(expenseId: number, expense: Omit<Expenses, 'id'>): Observable<Expenses> {
    return this.http.put<Expenses>(`${this.baseUrl}/${expenseId}`, expense);
  }

  deleteExpense(expenseId: number): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}/${expenseId}`)
  } 
}
