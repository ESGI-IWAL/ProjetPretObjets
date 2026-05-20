
import type { ICreateUserDto } from "~/dto/user/create.dto";
import type { ISearchUserDto } from "~/dto/user/search.dto";
import type { IUser } from "~/types/user";

const api = () => useNuxtApp().$api

export const createUser = async (dto: ICreateUserDto) => {
    return await api()("/users", {
        method: "POST",
        body: dto
    });
}

export const getUsers = async () => {
    return await api()<IUser[]>("/users")
}

export const searchUser = async (dto : ISearchUserDto) => {
    return await api()<IUser[]>("/users/search", {
        method: "POST",
        body: dto
    });
}

export const getUserById = async (id: string) => {
    return await api()<IUser>(`/users/${id}`)
}

export const getCurrentUser = async () => {
    return await api()<IUser>('/me')
}
// GET by ID
// POST RECHERCHE