import type { ICreateUserDto } from "~/dto/user/create.dto";
import type { ISearchUserDto } from "~/dto/user/search.dto";

  const api = useApi()

export const createUser = async (dto: ICreateUserDto) => {
    return await api("/users", {
        method: "POST",
        body: dto
    });
}

export const searchUser = async (dto : ISearchUserDto) => {
    return await api("/users/search", {
        method: "POST",
        body: dto
    });
}

// GET by ID
// POST RECHERCHE