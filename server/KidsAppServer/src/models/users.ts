import mongoose, { Schema, Document } from "mongoose";

interface IUser extends Document {
  username: string;
  password: string;
  score: number;
}

const UserSchema: Schema = new Schema({
  username: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  score: { type: Number, required: true },
});

const UserModel = mongoose.model<IUser>("User", UserSchema);

export { UserModel, IUser };
