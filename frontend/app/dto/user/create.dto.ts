import type { IUser } from "~/types/user";

export interface ICreateUserDto {
    user: IUser;
    password: string;
}