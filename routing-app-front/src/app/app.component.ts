import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterOutlet } from '@angular/router';
import {NavComponent} from "./nav/nav.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, RouterOutlet, NavComponent],
  template: `
    <app-nav>
      <router-outlet></router-outlet>
    </app-nav>
  `
})
export class AppComponent {}
