import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ExpenseDTO {
  description: string;
  amount: number;
}

export interface Summary {
  balance: number;
  expenses: ExpenseDTO[];
}

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private readonly apiUrl = 'http://localhost:8080/users/summary/1';

  constructor(private http: HttpClient) {}

  getSummary(): Observable<Summary> {
    return this.http.get<Summary>(this.apiUrl);
  }
}
