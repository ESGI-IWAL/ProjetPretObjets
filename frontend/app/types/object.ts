import { EObjectCategories} from '../enums/object/categories.enum';
import { EObjectMaterial } from '../enums/object/material.enum';
import { EObjectState } from '../enums/object/state.enum';

export interface IObject {
    id: number,
    ownerId: number
    name: string,
    images: string[],
    description:string,
    weight: number;
    dimensions: string;
    category: EObjectCategories,
    state: EObjectState,
    material: EObjectMaterial,
}
