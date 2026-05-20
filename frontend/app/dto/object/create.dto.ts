import { EObjectCategories } from '../../enums/object/categories.enum';
import { EObjectState } from '../../enums/object/state.enum';
import type { EObjectMaterial } from '~/enums/object/material.enum';

export interface ICreateObjectDto {
  images: string[];
  name: string;
  description: string;
  category: EObjectCategories;
  weight: number;
  dimensions: string;
  state: EObjectState;
  material: EObjectMaterial;
}