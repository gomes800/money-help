import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { DashboardService } from '../../services/dashboard.service';
import { routes } from '../../app.routes';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-add-balance',
  standalone: true,
  imports: [FormsModule, RouterModule],
  templateUrl: './add-balance.component.html',
  styleUrl: './add-balance.component.css'
})
export class AddBalanceComponent {
  userId = 1;
  value: number = 0;

  constructor(private dashboardService: DashboardService) {}

    onSubmit(): void {
    if (this.value  <= 0) {
      console.error('Valor deve ser maior que zero.')
      return
    }

    this.dashboardService.addBalance(this.value).subscribe({
      next: () => {
        console.log("Valor adicionado com sucesso.");
        this.value = 0;
      },
      error: (err) => {
        console.error("Erro ao adicionar valor.", err)
      }
    })
  }
}
