import { Injectable, Inject } from '@angular/core';
import {Step} from '../../models/step';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class StepService {

  constructor(private http: HttpClient, @Inject('API_URL') private API_URL: string) {
  }

  getStepByJourneyName(journeyName: string): Observable<Step[]> {
    return this.http.get<Step[]>(this.API_URL + `/Step/getStep/${journeyName}`)
      .pipe(catchError(this.errorHandler));
  }
  createStep(step: object): Observable<Response> {
    return this.http.post<Response>(this.API_URL + `/Step`, step)
      .pipe(catchError(this.errorHandler));
  }
  addLike(stepId: number): Observable<Response> {
    console.log(stepId);
    return this.http.post<Response>(this.API_URL + `/Step/like/${stepId}`, {})
      .pipe(catchError(this.errorHandler));
  }
  unLike(stepId: number): Observable<Response> {
    console.log(stepId);
    return this.http.post<Response>(this.API_URL + `/Step/unlike/${stepId}`, {})
      .pipe(catchError(this.errorHandler));
  }

  errorHandler(error: HttpErrorResponse) {
    return throwError(error.error || 'Server error');
  }
}

