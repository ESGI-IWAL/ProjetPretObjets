import { EObjectState } from '../../enums/object/state.enum';
import type { EObjectMaterial } from '~/enums/object/material.enum';
import { EObjectCategories } from '../../enums/object/categories.enum';

export interface ISearchObjectDto {
  category?: EObjectCategories;
  state?: EObjectState;
  material?: EObjectMaterial;
  disponibilityStartDate?: Date;
  disponibilityEndDate?: Date;
}