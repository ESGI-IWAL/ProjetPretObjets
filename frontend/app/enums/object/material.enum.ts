export enum EObjectMaterial {
PLASTIC = 'Plastique',
METAL = 'Métal',
WOOD = 'Bois',
FABRIC = 'Tissu',
GLASS = 'Verre',
CERAMIC = 'Céramique',
PAPER = 'Papier',
RUBBER = 'Caoutchouc',
OTHERS = 'Autres'
}

export const objectMaterialOptions = Object.entries(EObjectMaterial).map(([value, label]) => ({ value, label }));