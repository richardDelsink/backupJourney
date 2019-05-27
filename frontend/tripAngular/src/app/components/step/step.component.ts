import { Component, OnInit } from '@angular/core';
import {Step} from '../../models/step';
import {AuthService} from '../../services/auth/auth.service';
import {Router, ActivatedRoute} from '@angular/router';
import {StepService} from '../../services/step/step.service';
import {CommentService} from '../../services/comment/comment.service';
import {FormGroup, FormBuilder, Validators} from '@angular/forms';
import {Journey} from '../../models/journey';

@Component({
  selector: 'app-step',
  templateUrl: './step.component.html',
  styleUrls: ['./step.component.scss']
})
export class StepComponent implements OnInit {

  show = false;
  show2 = false;
  commentForm: FormGroup;
  stepForm: FormGroup;
  stepResults: Step[];
  userName: string;
  likeCounter: number;

  constructor(private stepService: StepService, private commentService: CommentService, private authService: AuthService,
              private router: Router, private formBuilder: FormBuilder, private route: ActivatedRoute) {
    this.commentForm = this.formBuilder.group({
      comment: ['', Validators.required]
    });
    this.stepForm = this.formBuilder.group({
      location: ['', Validators.required],
      stepName: ['', Validators.required],
      story: ['', Validators.required]
    });
    this.stepService.newSteps.subscribe(newStep => {
      this.stepResults.push(newStep);
    });

  }

  ngOnInit() {
    this.router.routeReuseStrategy.shouldReuseRoute = () => false;
    this.stepResults = null;
    delete this.stepResults;
    this.getStepsFromJourney();
    this.userName = localStorage.getItem('username');
    }

  getStepsFromJourney() {
    if (this.checkLoggedIn()) {
      this.stepService.getStepByJourneyName(localStorage.getItem('journey')).subscribe(data => {
        if (data != null) {
          this.stepResults = data;
          this.likeCounter = 0;
          this.likeCount();
        }
      });
    }
  }
  creatComment(stepId: number, userName: string) {
    if (this.checkLoggedIn() && this.commentForm.controls.comment.value) {
      this.commentService.createComment({comment: this.commentForm.controls.comment.value, stepId, userName}).subscribe(data => {
        this.getStepsFromJourney();
        this.commentForm.reset();
    }, error => {console.log('there was an error'); });
    }
  }
  creatSteps(journeys: Journey) {
    if (this.checkLoggedIn() && this.stepForm.controls.location.value) {
      this.stepService.createStep({ journey: journeys,
        location: this.stepForm.controls.location.value,
        stepName: this.stepForm.controls.stepName.value,
        story: this.stepForm.controls.story.value
      }).subscribe(data => {
        this.getStepsFromJourney();
        this.stepForm.reset();
        console.log('wtf is this ', JSON.stringify(data));
      }, error => {
        console.log('there was an error');
      });
    }
  }

  likeCount() {
    for (let i = 0; i < this.stepResults.length; i++) {
      this.likeCounter = this.likeCounter + this.stepResults[i].like.length;
    }
  }

  addLikes(stepId: number) {
    if (this.checkLoggedIn()) {
      this.stepService.addLike(stepId).subscribe(data => {
        this.getStepsFromJourney();
        // this.likeCount();
      }, error => {this.unLikes(stepId); });
    }
  }

  unLikes(stepId: number) {
    if (this.checkLoggedIn()) {
      this.stepService.unLike(stepId).subscribe(data => {
        this.getStepsFromJourney();
        // this.likeCount();
      }, error => {});
    }
  }
  checkLoggedIn(): boolean {
    if (this.authService.isLoggedIn() && localStorage.getItem('username') != null) {
      return true;
    } else {
      this.router.navigateByUrl('/login');
    }
  }

  toggle() {
    this.show = !this.show;
  }

  toggle2() {
    this.show2 = !this.show2;
  }
}
