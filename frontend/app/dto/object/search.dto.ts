import { EObjectState } from '../../enums/object/state.enum';
import type { EObjectMaterial } from '~/enums/object/material.enum';
import { EObjectCategories } from '../../enums/object/categories.enum';

export interface ISearchObjectWithDatesDto {
  name?: string;
  category: EObjectCategories |null;
  state: EObjectState | null;
  material: EObjectMaterial |null;
  disponibilityDate: string | null
}

export interface ISearchObjectDto {
  name?: string;
  category:  EObjectCategories |null;
  stateOfWear: EObjectState | null;
  material: EObjectMaterial |null;
}

export interface ISearchLendingWithIdsObjectsDto{
  idsObject: number[], 
  disponibilityDate: string | null
}

export interface IObjectInfoDisponibilityDto {
  id: number,
  nextLending : Date | null,
  endCurrentLending : Date | null,
  currentLendingStart?: Date | null
}
