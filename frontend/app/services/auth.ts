import type { ICreateUserDto } from "~/dto/user/create.dto";

const api = () => useNuxtApp().$api


export const login = async(email: string, password: string) :Promise<string> => {
    return await api()(("/api/auth/login"), {
        method: "POST",
        body: {
            email,
            password
        }
    })
}

export const register = async (createUserDto: ICreateUserDto):Promise<string> => {
    return await api()("/api/auth/register", {
        method: "POST",
        body: createUserDto
    });
}

