import { EObjectStateOfWear } from '../../enums/object/state-of-wear.enum';
import type { EObjectMaterial } from '~/enums/object/material.enum';
import { EObjectCategories } from '../../enums/object/categories.enum';
import type { EObjectStatus } from '~/enums/object/status.enum';

export interface ISearchObjectDto {
  category?: EObjectCategories;
  status?: EObjectStatus;
  stateOfWear?: EObjectStateOfWear;
  material?: EObjectMaterial;
}