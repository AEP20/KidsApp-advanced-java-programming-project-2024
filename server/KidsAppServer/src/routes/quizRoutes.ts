import { Router } from "express";
import { getQuestions } from "../controllers/quiz";

const router = Router();

router.get("/questions", getQuestions);

export default router;
