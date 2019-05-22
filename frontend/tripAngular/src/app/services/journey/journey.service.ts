import { Injectable, Inject } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { throwError, Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { catchError } from 'rxjs/operators';
import { Journey } from 'src/app/models/journey';


@Injectable({
  providedIn: 'root'
})
export class JourneyService {

  constructor(private http: HttpClient, @Inject('API_URL') private API_URL: string) {
  }

  getJourneyByName(journeyName: string): Observable<Journey[]> {
    return this.http.get<Journey[]>(this.API_URL + `/Journey/get/${journeyName}`)
      .pipe(catchError(this.errorHandler));
  }

  getJourneyByUser(userName: string) {
    return this.http.get<Journey[]>(this.API_URL + `/Journey/getByUser/${userName}`)
      .pipe(catchError(this.errorHandler));
  }
  errorHandler(error: HttpErrorResponse) {
    return throwError(error.error || 'Server error');
  }
}
