import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DashboardService, Summary } from '../../services/dashboard.service';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
})
export class DashboardComponent implements OnInit {

  summary: Summary | null = null;

  constructor(private dashboardService: DashboardService) {}

  ngOnInit(): void {
    this.dashboardService.getSummary().subscribe({
      next: (data) => (this.summary = data),
      error: (err) => console.error('Erro ao buscar resumo:', err),
    });
  }
}