import { Router } from "express";
import authRoutes from "./authRoutes";
import quizRoutes from "./quizRoutes"; 

const router = Router();

router.use("/auth", authRoutes);
router.use("/quiz", quizRoutes); 

export default router;
