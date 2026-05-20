// LOGIN
// REGISTER

import type { ICreateUserDto } from "~/dto/user/create.dto";
import type { IUser, IUserAuth } from "~/types/user";

const api = () => useNuxtApp().$api

export const login = async (email: string, password: string) => {
    return await api()<IUserAuth>("/auth/login", {
        method: "POST",
        body: { email, password }
    });
}

export const register = async (createUserDto: ICreateUserDto) => {
    return await api()<IUser>("/auth/register", {
        method: "POST",
        body: createUserDto
    });
}
