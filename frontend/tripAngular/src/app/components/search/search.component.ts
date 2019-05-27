import { Component, OnInit } from '@angular/core';
import {Journey} from '../../models/journey';
import { JourneyService } from 'src/app/services/journey/journey.service';
import { AuthService } from 'src/app/services/auth/auth.service';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {UserService} from '../../services/user/user.service';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.scss']
})
export class SearchComponent implements OnInit {
  journeyForm: FormGroup;
  searchResults: Journey[];
  search: string;


  constructor(private journeyService: JourneyService, private  userService: UserService, private authService: AuthService,
              private router: Router, private formBuilder: FormBuilder, private route: ActivatedRoute) {
    this.journeyForm = this.formBuilder.group({
    });
  }

  userName: string
  ngOnInit() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.getSearchResults();
    this.userName = localStorage.getItem('username');
  }
  getSearchResults() {
    if (this.checkLoggedIn()) {
      this.journeyService.getJourneyByUser(localStorage.getItem('username')).subscribe(data => {
        if (data != null) {
          this.searchResults = data;
          for (let i = 0; i < this.searchResults.length; i++) {
            console.log(JSON.stringify(this.searchResults[i].links, undefined, 4));
          }
        }
      });
    }
  }

  checkLoggedIn(): boolean {
      if (this.authService.isLoggedIn() && localStorage.getItem('username') != null) {
        return true;
      } else {
        this.router.navigateByUrl('/login');
      }
  }

  send(name: string) {
    localStorage.setItem('journey', name);
    console.log(JSON.stringify(localStorage.setItem('journey', name)));
    this.router.navigateByUrl('/step');
  }
}
