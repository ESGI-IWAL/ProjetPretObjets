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

export function toInputDate(value: Date | string | null | undefined): string {
  if (!value) return ""
  if (typeof value === "string") {
    const m = value.match(/^(\d{4}-\d{2}-\d{2})/)
    if (m && m[1]) return m[1]
    const d = new Date(value)
    if (isNaN(d.getTime())) return ""
    return formatDateForInput(d)
  }
  return formatDateForInput(value as Date)
}
