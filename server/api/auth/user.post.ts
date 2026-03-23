import type {IUser} from "../../../app/types/user";
import { defineEventHandler, readBody } from 'h3'

export default defineEventHandler(async (event: any): Promise<IUser|null> => {
    const body = await readBody(event) // Utilisez readBody au lieu de getQuery
    const username = body?.username as string | undefined
    const password = body?.password as string | undefined

    // Simuler les données utilisateur
    const user: IUser = {
        id: 1,
        pseudo: 'admin',
        email: 'admin@admin.com',
        avatar: 'https://images-ext-1.discordapp.net/external/g6cr53BQPmQ8wJXAiLgu2HhCnyM7L4C9HgudblNQaP0/https/wallpapers.com/images/featured-full/cool-profile-picture-87h46gcobjl5e4xu.jpg?format=webp&width=1028&height=1050',
        description: 'Just a test user',
        password: 'admin'
    };
    console.log(`Attempting login with username: ${username} and password: ${password}`);
    if (username && password) {
        console.log(`Attempting login with username: ${username} and password: ${password}`);
        if (username === user.pseudo && password === user.password) {
            return user; // Authentification échouée
        }
    }
    return null;
});