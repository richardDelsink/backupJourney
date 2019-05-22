import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError, Observable } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { User } from '../../models/user';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient, @Inject('API_URL') private API_URL: string) { }

  getProfile(username: string): Observable<User> {
    return this.http.get<User>(this.API_URL + `/user/${username}`)
      .pipe(catchError(this.errorHandler));
  }
  getUser(username: string) {
    return this.http.get<User>(this.API_URL + `/User/get/${username}`)
      .pipe(catchError(this.errorHandler));
  }

  /*getTimeline(username: string): Observable<Kweet[]> {
    return this.http.get<Kweet[]>(this.API_URL + `/kweet/${username}/timeline`)
      .pipe(catchError(this.errorHandler));
  }*/

  follow(username: string, userToFollow: string): Observable<Response> {
    return this.http.put<Response>(this.API_URL + `/user/${username}/following/add/${userToFollow}`, {})
      .pipe(catchError(this.errorHandler));
  }

  unfollow(username: string, userToUnfollow: string): Observable<Response> {
    return this.http.put<Response>(this.API_URL + `/user/${username}/following/remove/${userToUnfollow}`, {})
      .pipe(catchError(this.errorHandler));
  }

  getFollowers(username: string): Observable<User[]> {
    return this.http.get<User[]>(this.API_URL + `/user/${username}/followers`)
      .pipe(catchError(this.errorHandler));
  }

  getFollowing(username: string): Observable<User[]> {
    return this.http.get<User[]>(this.API_URL + `/user/${username}/following`)
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.error || 'Server error');
  }
}
