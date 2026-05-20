import type { ILending } from "~/types/lending"
import type { ICreateLendingDto } from "../dto/lending/create.dto"
import type { ISearchLendingDto } from "../dto/lending/search.dto"

const api = () => useNuxtApp().$api

export const createLending = async (
  dto: ICreateLendingDto
) => {
  return await api()("/lending", {
    method: "POST",
    body: dto
  })
}

export const getLendings = async () => {
  return await api()<ILending[]>("/lending")
}

export const getLendingById = async (id: string) => {
  return await api()<ILending>(`/lending/${id}`)
}

export const searchLending = async (searchParams: ISearchLendingDto) => {
  return await api()<ILending[]>("/lending/search", {
    method: "POST",
    body: searchParams
  })
}

export const updateLending = async (id: string, endDate: Date) => {
  
  return await api()(`/lending/${id}`, {
    method: "PUT",
    body: { endDate }
  })
}

export const deleteLending = async (id: string) => {
  return await api()(`/lending/${id}`, {
    method: "DELETE"
  })
}

// GET all
// GET by ID
// POST RECHERCHE { via objet, via utilisateur, via date debut, via date fin}
// POST CREATION { objet, utilisateur, date debut, date fin}
// PUT by ID { date fin }
// DELETE by Id  