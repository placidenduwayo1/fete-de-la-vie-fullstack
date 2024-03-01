import { IStage } from './stage.model';
import { IQuestion } from './question.model';

export interface IQuizz {
  id?: number;
  label?: string;
  stages?: IStage[] | null;
  questions?: IQuestion[] | null;
}

export class Quizz implements IQuizz {
  constructor(public id?: number, public label?: string, public stages?: IStage[] | null, public questions?: IQuestion[] | null) {}
}

export function getQuizzIdentifier(quizz: IQuizz): number | undefined {
  return quizz.id;
}
