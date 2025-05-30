import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {
  FormBuilder,
  ReactiveFormsModule,
  Validators,
  FormGroup,
} from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { Router, RouterModule } from '@angular/router';
import { environment } from '../../../environments/environments';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router
  ) {
    this.form = this.fb.group({
      name: ['', Validators.required],
      login: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(5)]],
    });
  }

  register() {
    if (this.form.invalid) return;

    const userData = this.form.value;

    this.http.post(`${environment.apiUrl}/auth/register`, userData).subscribe({
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
      },
    });
  }
}
