import { Injectable, Inject } from '@angular/core';

import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {SendComment} from '../../models/sendComment';

@Injectable({
  providedIn: 'root'
})
export class CommentService {

  constructor(private http: HttpClient, @Inject('API_URL') private API_URL: string) {
  }

  getComments(j: string): Observable<Comment[]> {
    return this.http.get<Comment[]>(this.API_URL + `Message/search/${j}`)
      .pipe(catchError(this.errorHandler));
  }
  createComment(comment: object): Observable<Response> {
    return this.http.post<Response>(this.API_URL + `/Message`, comment)
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.error || 'Server error');
  }
}
