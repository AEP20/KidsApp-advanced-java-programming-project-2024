import { Request, Response, NextFunction } from "express";
import { UserModel } from "../models/users";
import { sendErrorResponse, sendSuccessResponse } from "../response";

const login = async (req: Request, res: Response, next: NextFunction) => {
  const { username, password } = req.body;
  try {
    const user = await UserModel.findOne({ username: username });
    if (!user) {
      return sendErrorResponse({
        res,
        message: "USER_NOT_FOUND",
        code: 404,
      });
    }
    if (user.password !== password) {
      return sendErrorResponse({
        res,
        message: "WRONG_PASSWORD",
        code: 401,
      });
    }
    return sendSuccessResponse({
      res,
      message: "USER_LOGGED_IN_SUCCESSFULLY",
      data: { username: user.username },
      code: 200,
    });
  } catch (e) {
    next(e);
  }
};

const register = async (req: Request, res: Response, next: NextFunction) => {
  const { username, password } = req.body;
  try {
    const newUser = new UserModel({ username, password, score: 0 });
    await newUser.save();
    return sendSuccessResponse({
      res,
      message: "USER_REGISTERED_SUCCESSFULLY",
      data: { username: newUser.username },
      code: 201,
    });
  } catch (e) {
    next(e);
  }
};

export { login, register };
