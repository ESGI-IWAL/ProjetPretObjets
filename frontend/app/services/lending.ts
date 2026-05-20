import type { ICreateLendingDto } from "~/dto/lending/create.dto"
import type { ISearchLendingDto } from "~/dto/lending/search.dto"

export const createLending = async (
  dto: ICreateLendingDto
) => {
  return await $fetch("/loans", {
    method: "POST",
    body: dto
  })
}

export const getLendings = async () => {
  return await $fetch("/loans")
}

export const getLendingById = async (id: number) => {
  return await $fetch(`/loans/${id}`)
}

export const searchLendings = async (searchParams: ISearchLendingDto) => {
  return await $fetch("/loans/search", {
    method: "POST",
    body: searchParams
  })
}

export const updateLending = async (id: number, endDate: Date) => {
  return await $fetch(`/loans/${id}`, {
    method: "PUT",
    body: { endDate }
  })
}

export const deleteLending = async (id: number) => {
  return await $fetch(`/loans/${id}`, {
    method: "DELETE"
  })
}

// GET all
// GET by ID
// POST RECHERCHE { via objet, via utilisateur, via date debut, via date fin}
// POST CREATION { objet, utilisateur, date debut, date fin}
// PUT by ID { date fin }
// DELETE by Id  