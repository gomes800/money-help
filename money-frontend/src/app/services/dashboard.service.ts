import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface ExpenseDTO {
  name: string;
  description: string;
  amount: number;
}

export interface Summary {
  balance: number;
  expenses: ExpenseDTO[];
}

export interface BalanceRequest {
  amount: number;
}

@Injectable({
  providedIn: 'root',
})
export class DashboardService {
  private readonly baseUrl = `${environment.apiUrl}/users`;

  constructor(private http: HttpClient) {}

  getSummary(): Observable<Summary> {
    return this.http.get<Summary>(`${this.baseUrl}/summary`);
  }

  addBalance(value: number): Observable<void> {
    const payload = { value }
    return this.http.post<void>(`${this.baseUrl}/addBalance`, payload)
  }
}
