import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  messageForm: FormGroup;
  submitted = false;
  verifiedError = false;
  passwordError = false;
  notFoundError = false;

  constructor(private authService: AuthService, private formBuilder: FormBuilder, private router: Router) {
    this.messageForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  ngOnInit() {
  }

  onSubmit() {
    this.submitted = true;

    if (this.messageForm.invalid) {
      return;
    }

    this.authService.login({ username: this.messageForm.controls.username.value, password: this.messageForm.controls.password.value })
      .subscribe((data) => {
        this.router.navigateByUrl('/search');
      },
        error => {
          this.notFoundError = false;
          this.verifiedError = false;
          this.passwordError = false;
          if (error.status === 404) {
            this.notFoundError = true;
          } else if (error.status === 400) {
            this.passwordError = true;
          }

        });
  }

}
