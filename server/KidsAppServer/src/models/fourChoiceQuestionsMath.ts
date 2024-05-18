import mongoose, { Schema, Document } from "mongoose";

interface IFourChoiceQuestionMath extends Document {
  question: string;
  questionString: string;
  choiceA: string;
  choiceB: string;
  choiceC: string;
  choiceD: string;
  correctAnswer: string;
}

const FourChoiceQuestionMathSchema: Schema = new Schema({
  question: { type: String, required: true },
  questionString: { type: String, required: true },
  choiceA: { type: String, required: true },
  choiceB: { type: String, required: true },
  choiceC: { type: String, required: true },
  choiceD: { type: String, required: true },
  correctAnswer: { type: String, required: true, enum: ["A", "B", "C", "D"] },
});

const FourChoiceQuestionMathModel = mongoose.model<IFourChoiceQuestionMath>(
  "fourChoiceQuestionsMath",
  FourChoiceQuestionMathSchema
);

export { FourChoiceQuestionMathModel, IFourChoiceQuestionMath };
