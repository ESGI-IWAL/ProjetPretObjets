import type { ICreateUserDto } from "~/dto/user/create.dto";
import type {IAuthResponse} from "~/dto/auth/login.dto";

const api = () => useNuxtApp().$api


export const login = async (email: string, password: string): Promise<string> => {
    const response:IAuthResponse = await api()("/api/auth/login", {
        method: "POST",
        body: { email, password }
    })

    return response.token
}

export const register = async (createUserDto: ICreateUserDto):Promise<string> => {
    return await api()("/api/auth/register", {
        method: "POST",
        body: createUserDto
    });
}

