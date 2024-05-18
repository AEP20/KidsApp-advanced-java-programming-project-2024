import express from "express";
import cors from "cors";
import dotenv from "dotenv";
import { Request, Response, NextFunction } from "express";
import { connectToDatabase } from "./config/db";
import routes from "./routes/routes";

const app = express();

dotenv.config();
app.use(cors());
app.use(express.json());

const port = process.env.PORT || 3000;

app.use((req: Request, res: Response, next: NextFunction) => {
  const startTime = Date.now();

  res.on("finish", () => {
    const endTime = Date.now();
    const responseTime = endTime - startTime;
    console.log(
      `${req.method} ${req.originalUrl} - ${res.statusCode} ${responseTime}ms`
    );
  });

  next();
});

const startApp = async () => {
  try {
    await connectToDatabase();

    app.get("/", (req: Request, res: Response) => {
      res.send("KidsAppServer");
    });

    app.use("/api", routes);

    app.listen(port, () => {
      console.log(`App listening at http://localhost:${port}`);
    });
  } catch (error) {
    console.error("Error starting the app:", error);
  }
};

startApp();
