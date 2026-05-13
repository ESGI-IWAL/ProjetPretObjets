import type { IUser } from "~/types/user";

export const createUser = async (userData: { user: IUser; email: string; password: string }) => {
    return await $fetch("/users", {
        method: "POST",
        body: userData
    });
}



// GET by ID
// POST RECHERCHE