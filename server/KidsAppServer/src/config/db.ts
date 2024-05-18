import mongoose from "mongoose";

const url = process.env.DB_URL || "mongodb://localhost:27017/kidsAppDatabase";

console.log("url", url);

export async function connectToDatabase() {
  try {
    await mongoose.connect(url);
    console.log("Connected successfully to MongoDB with Mongoose", url);
  } catch (err) {
    console.error("Failed to connect to MongoDB", err);
    process.exit(1);
  }
}
