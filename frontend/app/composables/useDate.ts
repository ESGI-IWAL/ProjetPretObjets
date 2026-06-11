export function formatDateLong(input: string | Date | null | undefined): string {
  if (!input) return ''
  const d = input instanceof Date ? input : new Date(input)
  if (Number.isNaN(d.getTime())) return String(input)
  return new Intl.DateTimeFormat('fr-FR', {
    weekday: 'long',
    day: 'numeric',
    month: 'long',
    year: 'numeric',
  }).format(d)
}

export function useDate() {
  return {
    formatDateLong,
  }
}

export default formatDateLong

export function formatDateForInput(d: Date | null | undefined): string {
  if (!d) return ""
  const date = d instanceof Date ? d : new Date(d)
  if (Number.isNaN(date.getTime())) return ""
  const yyyy = date.getFullYear()
  const mm = String(date.getMonth() + 1).padStart(2, "0")
  const dd = String(date.getDate()).padStart(2, "0")
  return `${yyyy}-${mm}-${dd}`
}

