import mongoose, { Schema, Document } from "mongoose";

interface IDirectionQuestion extends Document {
  question: string;
  questionString: string;
  choiceA: string;
  choiceB: string;
  choiceC: string;
  choiceD: string;
  correctAnswer: string;
}

const DirectionQuestionSchema: Schema = new Schema({
  question: { type: String, required: true },
  questionString: { type: String, required: true },
  choiceA: { type: String, required: true },
  choiceB: { type: String, required: true },
  choiceC: { type: String, required: true },
  choiceD: { type: String, required: true },
  correctAnswer: { type: String, required: true, enum: ["A", "B", "C", "D"] },
});

const DirectionQuestionModel = mongoose.model<IDirectionQuestion>(
  "fourChoiceQuestionsDirection",
  DirectionQuestionSchema
);

export { DirectionQuestionModel, IDirectionQuestion };
