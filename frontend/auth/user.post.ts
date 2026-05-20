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
        description: "Décrire consiste à représenter quelque chose avec des mots de manière précise et compréhensible. Cela permet de donner une image mentale d’un objet, d’un lieu, d’une personne ou d’une situation à celui qui lit ou écoute. Une bonne description met en avant les caractéristiques importantes, comme les formes, les couleurs, les sensations ou les détails particuliers. Elle aide à mieux comprendre, imaginer et se représenter ce qui est décrit. Décrire, c’est aussi choisir les bons mots pour être clair et fidèle à la réalité, tout en rendant le contenu agréable à lire. Cela peut être utile dans de nombreux contextes, que ce soit pour expliquer, raconter ou transmettre une idée. En résumé, décrire permet de rendre visible par les mots ce qui ne l’est pas directement.",
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