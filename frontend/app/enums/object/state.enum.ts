export enum EObjectState {
  NEW = "Nouveau",
  GOOD = "Bon",
  WORN = "Usé",
  DAMAGED = "Endommagé"
}

export const objectStateOptions = Object.entries(EObjectState).map(([value, label]) => ({ value, label }));
