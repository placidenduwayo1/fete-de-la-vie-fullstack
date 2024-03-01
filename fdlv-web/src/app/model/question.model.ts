import { IQuizz } from './quizz.model';
import { Answer, IAnswer } from './answer.model';
import { ResponseType } from './response-type.model';

export interface IQuestion {
  id?: number;
  guid?:number| string;
  label?: string;
  text?: string;
  type?: ResponseType;
  quizz?: IQuizz | null;
  answers?: IAnswer[] | null;
  order?: number;
  prevOrder?: number;
}

export class Question implements IQuestion {
  constructor(
    public id?: number,
    public guid?:number| string,
    public label?: string,
    public text?: string,
    public type?: ResponseType,
    public quizz?: IQuizz | null,
    public answers?: IAnswer[] | null,
    public order?: number,
    public prevOrder?: number
  ) {
  }

  static clone(question: Question): Question  {
    return new Question(  
      question.id,
      question.guid,
      question.label,
      question.text,
      question.type,
      question.quizz,
      question.answers.map(answer => Answer.clone(answer)),
      question.order,
      question.prevOrder);
  }
}

export function getQuestionIdentifier(question: IQuestion): number | undefined {
  return question.id;
}

