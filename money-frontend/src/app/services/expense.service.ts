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

@Injectable({
  providedIn: 'root'
})
export class ExpenseService {
  private readonly apiUrl= `${environment.apiUrl}/expenses/all/1`

  constructor(private http: HttpClient) { }

  getMyExpenses(): Observable<Expenses[]> {
    return this.http.get<Expenses[]>(this.apiUrl);
  }

  addExpense(expense: Omit<Expenses, 'id'>): Observable<Expenses> {
    const url = `${environment.apiUrl}/expenses/insert/1`;
    return this.http.post<Expenses>(url, expense);
  }
}
