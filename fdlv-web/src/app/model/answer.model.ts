import {IQuestion} from "./question.model";

export interface IAnswer {
  id?: number;
  reponse?: string;
  isCorrect?: boolean;
  question?: IQuestion | null;
}

export class Answer implements IAnswer {
  constructor(public id?: number, public reponse?: string, public isCorrect?: boolean, public question?: IQuestion | null) {
    this.isCorrect = this.isCorrect ?? false;
  }

  static clone(answer: Answer){
    return new Answer(
      answer.id, 
      answer.reponse, 
      answer.isCorrect, 
      answer.question) 
   
  }
}

export function getAnswerIdentifier(answer: IAnswer): number | undefined {
  return answer.id;
}
