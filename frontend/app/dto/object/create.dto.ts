import type { EObjectStatus } from '~/enums/object/status.enum';
import { EObjectCategories } from '../../enums/object/categories.enum';
import { EObjectStateOfWear } from '../../enums/object/state-of-wear.enum';
import type { EObjectMaterial } from '~/enums/object/material.enum';

export interface ICreateObjectDto {
  image: string;
  name: string;
  description: string;
  category: EObjectCategories;
  disponibility: EObjectStatus.DISPONIBLE;
  weight: number;
  dimensions: string;
  stateOfWear: EObjectStateOfWear;
  material: EObjectMaterial;
}