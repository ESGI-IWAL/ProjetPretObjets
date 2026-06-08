export enum EObjectCategories {
ELECTRONICS = 'Electronique',
FURNITURE = 'Meubles',
TOOLS = 'Outils',
CLOTHING = 'Vêtements',
BOOKS = 'Livres',
TOYS = 'Jouets',
SPORTS = 'Sports',
OTHERS = 'Autres'
}

export const objectCategoryOptions = Object.entries(EObjectCategories).map(
  ([key, label]) => ({
    value: key,
    label,
  })
)