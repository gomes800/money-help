import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, ReactiveFormsModule, Validators, FormGroup } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { environment } from '../../../environments/environments';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './register.component.html',
})
export class RegisterComponent {

  form!: FormGroup;  // 👈 Define primeiro, mas não instancia ainda

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    // 👇 Inicializa aqui
    this.form = this.fb.group({
      name: ['', Validators.required],
      login: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(5)]],
    });
  }

  register() {
    if (this.form.invalid) return;

    const userData = this.form.value;

    this.http.post(`${environment.apiUrl}/auth/register`, userData)
      .subscribe({
        next: () => {
          alert('Registro realizado com sucesso!');
          this.router.navigate(['/login']);
        },
        error: (err) => {
          if (err.status === 400) {
            alert(err.error);
          } else {
            alert('Erro ao registrar. Tente novamente.');
          }
        }
      });
  }
}
