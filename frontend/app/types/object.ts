import { EObjectCategories} from '../enums/object/categories.enum';
import { EObjectMaterial } from '../enums/object/material.enum';
import { EObjectStateOfWear } from '../enums/object/state-of-wear.enum';
import { EObjectStatus } from '../enums/object/status.enum';

export interface IObject {
    id:number,
    name: string,
    image: string,
    description:string,
    category: EObjectCategories,
    status: EObjectStatus,
    stateOfWear: EObjectStateOfWear,
    material: EObjectMaterial,
    ownerId: number
}
