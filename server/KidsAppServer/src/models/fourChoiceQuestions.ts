import mongoose, { Schema, Document } from "mongoose";

interface IFourChoiceQuestion extends Document {
  question: string;
  choiceA: string;
  choiceB: string;
  choiceC: string;
  choiceD: string;
  correctAnswer: string;
}

const FourChoiceQuestionSchema: Schema = new Schema({
  question: { type: String, required: true },
  choiceA: { type: String, required: true },
  choiceB: { type: String, required: true },
  choiceC: { type: String, required: true },
  choiceD: { type: String, required: true },
  correctAnswer: { type: String, required: true, enum: ["A", "B", "C", "D"] },
});

const FourChoiceQuestionModel = mongoose.model<IFourChoiceQuestion>(
  "fourChoiceQuestions",
  FourChoiceQuestionSchema
);

export { FourChoiceQuestionModel, IFourChoiceQuestion };
