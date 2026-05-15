import type { IUser } from "~/types/user";

export interface ICreateUserDto {
    userInfo: Omit<IUser, "id">;
    email: string;
    password: string;
}