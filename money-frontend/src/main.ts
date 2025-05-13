import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { provideRouter } from '@angular/router';
import { AppComponent } from './app/app.component';
import { DashboardComponent } from './app/pages/dashboard/dashboard.component'; // ajuste o caminho se necessário
import { HomeComponent } from './app/pages/home/home.component';

bootstrapApplication(AppComponent, {
  providers: [
    provideHttpClient(),
    provideRouter([
      { path: '', component: HomeComponent },
      { path: 'dashboard', component: DashboardComponent },
    ])
  ]
});
