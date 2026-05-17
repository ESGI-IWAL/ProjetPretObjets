import type { IUser } from "~/types/user";

export interface ICreateUserDto {
    userInfo: Omit<IUser,  "id" | "description" | "image">;
    email: string;
    password: string;
}