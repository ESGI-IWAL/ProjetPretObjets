import { EObjectCategories } from '../../enums/object/categories.enum';
import { EObjectState } from '../../enums/object/state.enum';
import type { EObjectMaterial } from '~/enums/object/material.enum';

export interface ICreateObjectDto {
  images: string[];
  name: string;
  description: string;
  category: keyof typeof EObjectCategories | null;
  weight: number;
  dimensions: string;
  state: keyof typeof EObjectState | null;
  material: keyof typeof EObjectMaterial | null;
}