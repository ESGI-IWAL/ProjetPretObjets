import type {IUser} from "../../../app/types/user";
import { defineEventHandler, readBody } from 'h3'

export default defineEventHandler(async (event: any): Promise<IUser|null> => {
    const body = await readBody(event) // Utilisez readBody au lieu de getQuery
    const username = body?.username as string | undefined
    const password = body?.password as string | undefined

    // Simuler les données utilisateur
    const user: IUser = {
        id: 1,
        pseudo: 'john_doe',
        email: 'test@test.com',
        avatar: 'https://example.com/avatar.jpg',
        description: 'Just a test user',
        password: 'hashed_password'
    };
    console.log(`Attempting login with username: ${username} and password: ${password}`);
    if (username && password) {
        console.log(`Attempting login with username: ${username} and password: ${password}`);
        // Ici, vous pouvez ajouter une logique pour vérifier le nom d'utilisateur et le mot de passe
        if (username === user.pseudo && password === user.password) {
            return user; // Authentification échouée
        }
    }
    return null;
});