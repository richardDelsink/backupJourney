import { Comment } from './comment';
import { Journey } from './journey';
import {User} from './user';

export class Step {
  stepId: number;
  stepName: string;
  location: string;
  postDate: string;
  story: string;
  like: User[];
  whoCanSee: string;
  userName: string;
  links: any;
  messages: Comment[];
  journey: Journey;
}
