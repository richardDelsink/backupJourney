import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../services/auth/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss'],
})
export class HeaderComponent implements OnInit {

  userName: string;
  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  loggedOut() {
    this.authService.logout();
    this.checkLoggedIn();
  }
  checkLoggedIn(): boolean {
    if (this.authService.isLoggedIn() && localStorage.getItem('username') != null ) {
      this.userName = localStorage.getItem('username');
      return true;
    } else {
      this.router.navigateByUrl('/login');
    }
  }
}
