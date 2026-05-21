export enum ELendingStatus {
  IN_PROGRESS = "En cours",
  PENDING = "En attente",
  VALIDATED = "Validé",
  REFUSED = "Refusé",
  COMPLETED = "Terminé",
  CANCELED = "Annulé"
}

export type TLendingStatus = typeof ELendingStatus[keyof typeof ELendingStatus]