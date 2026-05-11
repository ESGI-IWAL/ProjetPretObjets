import type { EObjectStatus } from '~/enums/object-status.enum';
import { EObjectCategories } from '../enums/object-categories.enum';
import { EObjectStateOfWear } from '../enums/object-state-of-wear.enum';

export interface ICreateObjectDto {
  image: string;
  nom: string;
  description: string;
  categorie: EObjectCategories;
  disponibilite: EObjectStatus.DISPONIBLE;
  poids: number;
  dimensions: string;
  usure: EObjectStateOfWear;
  matiere: string;
}