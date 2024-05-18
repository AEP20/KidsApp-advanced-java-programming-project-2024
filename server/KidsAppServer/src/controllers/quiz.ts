import { Request, Response, NextFunction } from "express";
import { FourChoiceQuestionModel } from "../models/fourChoiceQuestions";
import { FourChoiceQuestionMathModel } from "../models/fourChoiceQuestionsMath";
import { SeasonQuestionModel } from "../models/fourChoiceQuestionsSeason";
import { sendErrorResponse, sendSuccessResponse } from "../response";
import { DirectionQuestionModel } from "../models/fourChoiceQuestionsDirection";

const shuffleArray = (array: any[]) => {
  for (let i = array.length - 1; i > 0; i--) {
    const j = Math.floor(Math.random() * (i + 1));
    [array[i], array[j]] = [array[j], array[i]];
  }
  return array;
};

const getQuestions = async (
  req: Request,
  res: Response,
  next: NextFunction
) => {
  try {
    const { type } = req.query;
    const language = req.query.language || "tr";
    let questions;

    if (type === "math") {
      questions = await FourChoiceQuestionMathModel.find({ language });
    } else if (type === "clock") {
      questions = await FourChoiceQuestionModel.find({ language });
    } else if (type === "seasons") {
      questions = await SeasonQuestionModel.find({ language });
    } else if (type === "direction") {
      questions = await DirectionQuestionModel.find({ language });
    } else {
      return sendErrorResponse({
        res,
        message: "INVALID_TYPE",
        code: 400,
      });
    }

    const shuffledQuestions = shuffleArray(questions);
    return sendSuccessResponse({
      res,
      message: "QUESTIONS_FETCHED_SUCCESSFULLY",
      data: shuffledQuestions,
      code: 200,
    });
  } catch (e) {
    next(e);
  }
};

export { getQuestions };
