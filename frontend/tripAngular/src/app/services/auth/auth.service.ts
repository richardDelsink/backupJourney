import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { tap } from 'rxjs/operators';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  jwtHelper: JwtHelperService = new JwtHelperService();

  constructor(private http: HttpClient, @Inject('API_URL') private API_URL: string) { }

  register(user: any): Observable<object> {
    return this.http.post(this.API_URL + '/auth/register', user)
      .pipe(catchError(this.errorHandler));
  }

  login(userCredentials: any) {
    return this.http.post<object>(this.API_URL + '/auth/login', userCredentials, { observe: 'response' })
      .pipe(tap((res) => {
        localStorage.setItem('username', userCredentials.username);
        this.setSession(res.headers.get('Authorization').slice(7)); // Slice "Bearer "
      }));
  }

  verify(key: any): Observable<object> {
    return this.http.put(this.API_URL + `/auth/register/verify/${key}`, {})
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.error || 'Server error');
  }

  private setSession(token) {
    localStorage.setItem('token', token);
  }

  logout() {
    localStorage.removeItem('token');
  }

  public isLoggedIn() {
    const token = localStorage.getItem('token');
    return !this.jwtHelper.isTokenExpired(token);
  }

  isLoggedOut() {
    return !this.isLoggedIn();
  }

}
