export enum ELendingStatus {
  AVAILABLE = "AVAILABLE",
  IN_PROGRESS = "IN_PROGRESS",
  PENDING = "PENDING",
  VALIDATED = "VALIDATED",
  REFUSED = "REFUSED",
  COMPLETED = "COMPLETED",
  CANCELED = "CANCELED"
}

export const ELendingStatusLabel: Record<ELendingStatus, string> = {
  AVAILABLE: "Disponible",
  IN_PROGRESS: "En cours",
  PENDING: "En attente",
  VALIDATED: "Validé",
  REFUSED: "Refusé",
  COMPLETED: "Terminé",
  CANCELED: "Annulé"
}

export const normalizeLendingStatus = (
  status?: string | ELendingStatus | null
): ELendingStatus | undefined => {
  if (!status) {
    return undefined
  }

  const raw = status.toString().trim()
  const upper = raw.replace(/[-\s]+/g, '_').toUpperCase()

  if (Object.values(ELendingStatus).includes(upper as ELendingStatus)) {
    return upper as ELendingStatus
  }

  switch (raw.toLowerCase()) {
    case 'in_progress':
    case 'in-progress':
    case 'in progress':
      return ELendingStatus.IN_PROGRESS
    case 'pending':
      return ELendingStatus.PENDING
    case 'validated':
      return ELendingStatus.VALIDATED
    case 'refused':
      return ELendingStatus.REFUSED
    case 'completed':
      return ELendingStatus.COMPLETED
    case 'canceled':
    case 'cancelled':
      return ELendingStatus.CANCELED
    case 'available':
      return ELendingStatus.AVAILABLE
    default:
      return undefined
  }
}

export const getLendingStatusLabel = (
  status?: string | ELendingStatus | null
): string => {
  const normalized = normalizeLendingStatus(status)
  return normalized ? ELendingStatusLabel[normalized] : String(status ?? '')
}