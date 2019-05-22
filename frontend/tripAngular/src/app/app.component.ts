import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'TripJourney';

  loggedIn: boolean;

  constructor(private router: Router) { }

 /* headerPage(): boolean {
    return this.router.url === '/timeline' || this.router.url === '/mentions' ||
      this.router.url.startsWith('/profile') || this.router.url.startsWith('/search');
  }*/

}
