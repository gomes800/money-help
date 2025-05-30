import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  Validators,
  ReactiveFormsModule,
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.form = this.fb.group({
      login: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(5)]],
    });
  }

  login() {
    if (this.form.invalid) return;

    this.authService.login(this.form.value).subscribe({
      next: (res) => {
        this.authService.saveToken(res.token);
        alert('Login realizado com sucesso!');
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        alert('Login inválido!');
      },
    });
  }
}
