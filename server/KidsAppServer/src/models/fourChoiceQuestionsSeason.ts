import mongoose, { Schema, Document } from "mongoose";

interface ISeasonQuestion extends Document {
  question: string;
  questionString: string;
  choiceA: string;
  choiceB: string;
  choiceC: string;
  choiceD: string;
  correctAnswer: string;
}

const SeasonQuestionSchema: Schema = new Schema({
  question: { type: String, required: true },
  questionString: { type: String, required: true },
  choiceA: { type: String, required: true },
  choiceB: { type: String, required: true },
  choiceC: { type: String, required: true },
  choiceD: { type: String, required: true },
  correctAnswer: { type: String, required: true, enum: ["A", "B", "C", "D"] },
  language: { type: String, required: true }
});

const SeasonQuestionModel = mongoose.model<ISeasonQuestion>(
  "fourChoiceQuestionsSeason",
  SeasonQuestionSchema
);

export { SeasonQuestionModel, ISeasonQuestion };
