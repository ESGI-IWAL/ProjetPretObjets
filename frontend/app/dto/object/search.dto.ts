import { EObjectState } from '../../enums/object/state.enum';
import type { EObjectMaterial } from '~/enums/object/material.enum';
import { EObjectCategories } from '../../enums/object/categories.enum';

export interface ISearchObjectDto {
  name?: string;
  category: EObjectCategories |null;
  state: EObjectState | null;
  material: EObjectMaterial |null;
  disponibilityStartDate: Date | null;
  disponibilityEndDate: Date | null;
}

export interface ISearchLendingWithIdsObjectsDto{
  idsObject: number[], 
  disponibilityStartDate: Date | null,
  disponibilityEndDate: Date | null
}

export interface IObjectInfoDisponibilityDto {
  id: number,
  nextLending : Date | null,
  endCurrentLending : Date | null
}
