import { Injectable, Inject } from '@angular/core';
import {Step} from '../../models/step';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {Observable, Subject, throwError} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {WebsocketService} from '../websocket/websocket.service';

@Injectable({
  providedIn: 'root'
})
export class StepService {

  public newSteps: Subject<Step>;

  constructor(private http: HttpClient, @Inject('API_URL') private API_URL: string, wsService: WebsocketService) {
    this.newSteps = wsService.connect('ws://localhost:8080/TripJourney/websocket/' + localStorage.getItem('token')).pipe(map(
      (response: MessageEvent): Step => {
        const data = JSON.parse(response.data);
        return {
          stepId: data.stepId,
          stepName: data.stepName,
          location: data.location,
          postDate: data.postDate,
          story: data.story,
          like: null,
          whoCanSee: '',
          userName: '',
          links: null,
          messages: null,
          journey: null
        };
      }
    )) as Subject<Step>;
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

