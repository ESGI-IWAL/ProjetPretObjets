
import type { ICreateUserDto } from "~/dto/user/create.dto";
import type { ISearchUserDto } from "~/dto/user/search.dto";
import type { IUpdateUserDto } from "~/dto/user/update.dto";
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

export const getUserById = async (id: number) => {
    return await api()<IUser>(`/users/${id}`)
}

export const getCurrentUser = async () => {
    return await api()<IUser>('/me')
}

export const updateUser = async (dto: IUpdateUserDto) => {
    return await api()(`/users/${dto.id}`, {
        method: "PUT",
        body: dto
    });
}
// GET by ID
// POST RECHERCHE